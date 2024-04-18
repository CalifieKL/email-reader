package io.github.califiekl.email.reader.web.msgraphapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.califiekl.email.reader.web.AuthTokenGetter;
import io.github.califiekl.email.reader.web.EmailReader;
import io.github.califiekl.email.reader.web.client.SampleMailboxConfigGetter;
import io.github.califiekl.email.reader.util.EmailReaderUIException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailReaderMSGraphAPIImpl extends EmailReader {

    private final String BEARER = "Bearer ";
    private final String AUTHORIZATION = "Authorization";
    private final String ENCODING_STANDARD = "UTF-8";
    private final String MS_GRAPH_API_BASE = "https://graph.microsoft.com/v1.0/";

    //private static Logger logger = Logger.getLogger(EmailReaderMSGraphAPIImpl.class);

    public EmailReaderMSGraphAPIImpl(AuthTokenGetter tokenGetter){ super(tokenGetter); }

    @Override
    protected void setServiceAccountGetter(){
        mailboxConfigurationGetter = new SampleMailboxConfigGetter();
    }

    @Override
    public List<Map<String, String>> read(){
        String accessToken = tokenGetter.getAuthToken();
        try {
            String emailEntityString = getMessageEntityString(accessToken);
            return readFrom(emailEntityString);
        } catch (IOException ex) {
            throw new EmailReaderUIException("cannot read email: "+ ex.getMessage());
        }
    }

    public List<Map<String, String>> readFrom(String messageEntityString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, Object>> returnEntityTypeRef = new TypeReference<Map<String, Object>>() {};
        Map<String, Object> returnedEntity = mapper.readValue(messageEntityString, returnEntityTypeRef);
        List<Map<String, String>> parsedMessages = new ArrayList<Map<String,String>>();
        try{
            List<Map<String,Object>> messages = (ArrayList<Map<String,Object>>)returnedEntity.get(MESSAGES_DATA_FIELD);
            for(Map<String,Object> message : messages){
                Map<String, String> parsedMessageData = getTargetedFieldsFromFullMessageData(message);
                if(parsedMessageData.get(MessageGlossary.SENDER.getFieldName()).equals(
                        parsedMessageData.get(MessageGlossary.FROM.getFieldName())))
                    parsedMessages.add(parsedMessageData);
            }
        }catch(Exception ex){
            throw new EmailReaderUIException("error getting messages from returned entity: "+ex.getMessage());
        }
        return parsedMessages;
    }

    private Map<String, String> getTargetedFieldsFromFullMessageData(Map<String, Object> messageData) {
        Map<String, String> parsedMessageData = new HashMap<String, String>();
        for(MessageGlossary field: MessageGlossary.values()) {
            Object fieldValue = messageData.get(field.getFieldName());
            if(null ==  fieldValue) continue;
            parsedMessageData.put(field.getFieldName(), fieldValue.toString());
        }
        return parsedMessageData;
    }

    private String getMessageEntityString(String accessToken) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getMessages = new HttpGet(getMessageRequestURL());
        getMessages.setHeader(AUTHORIZATION, BEARER+accessToken);
        CloseableHttpResponse getMessageResponse = client.execute(getMessages);
        String messageEntityString = EntityUtils.toString(getMessageResponse.getEntity());
        System.out.println(messageEntityString);
        return messageEntityString;
    }

    private String getMessageRequestURL() {
        StringBuilder requestURLBuilder = new StringBuilder();
        requestURLBuilder.append(MS_GRAPH_API_BASE);
        requestURLBuilder.append(tokenGetter.getClientApp().getTenantId());
        requestURLBuilder.append("/users/");
        try {
            requestURLBuilder.append(URLEncoder.encode(mailboxConfigurationGetter.getServiceAccountId(), ENCODING_STANDARD));
        } catch (UnsupportedEncodingException e) {
            throw new EmailReaderUIException("cannot encode user: "+e.getMessage());
        }
        requestURLBuilder.append(mailboxConfigurationGetter.getMailFolderUrl());
        requestURLBuilder.append(mailboxConfigurationGetter.getReadingOption());
        return requestURLBuilder.toString();
    }
}
