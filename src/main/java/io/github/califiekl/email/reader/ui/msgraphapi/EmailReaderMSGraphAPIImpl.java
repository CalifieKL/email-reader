package io.github.califiekl.email.reader.ui.msgraphapi;


import io.github.califiekl.email.reader.ui.AuthTokenGetter;
import io.github.califiekl.email.reader.ui.ClientApplication;
import io.github.califiekl.email.reader.ui.EmailReader;
import io.github.califiekl.email.reader.ui.EmailReaderUIConfig;
import io.github.califiekl.email.reader.ui.client.SampleServiceAccountGetter;
import io.github.califiekl.email.reader.util.EmailReaderUIException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class EmailReaderMSGraphAPIImpl extends EmailReader {

    private final String BEARER = "Bearer ";
    private final String AUTHORIZATION = "Authorization";

    public EmailReaderMSGraphAPIImpl(AuthTokenGetter tokenGetter){ super(tokenGetter);}

    @Override
    protected void setServiceAccountGetter(){
        accountGetter = new SampleServiceAccountGetter();
    }

    @Override
    public List<Map<String, String>> read(){
        String accessToken = tokenGetter.getAuthToken();
        System.out.println("accessToken: " + accessToken);
        try {
            String emailEntityString = getMessageEntityString(accessToken);
            System.out.println(emailEntityString);
            return readFrom(emailEntityString);
        } catch (IOException ex) {
            throw new EmailReaderUIException("cannot read email: "+ ex.getMessage());
        }
    }

    public List<Map<String, String>> readFrom(String messageEntityString) throws IOException {

        return null;
    }

    private String getMessageEntityString(String accessToken) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getMessages = new HttpGet(getMessageRequestURL());
        getMessages.setHeader(AUTHORIZATION, BEARER+accessToken);
        CloseableHttpResponse getMessageResponse = client.execute(getMessages);
        String messageEntityString = EntityUtils.toString(getMessageResponse.getEntity());
        return messageEntityString;
    }

    private String getMessageRequestURL() {
        StringBuilder requestURLBuilder = new StringBuilder();
        requestURLBuilder.append("https://graph.microsoft.com/v1.0/");
        requestURLBuilder.append(tokenGetter.getClientApp().getTenantId());
        requestURLBuilder.append("/users/");
        try {
            requestURLBuilder.append(URLEncoder.encode(accountGetter.getServiceAccountId(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new EmailReaderUIException("cannot encode user: "+e.getMessage());
        }
        requestURLBuilder.append("/mailFolders/Inbox/messages");
        System.out.println(requestURLBuilder.toString());
        return requestURLBuilder.toString();
    }
}
