package io.github.califiekl.email.reader.ui.msgraphapi;


import io.github.califiekl.email.reader.ui.EmailReader;
import io.github.califiekl.email.reader.ui.EmailReaderUIConfig;
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

public class EmailReaderMSGraphAPIImpl implements EmailReader {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    @Override
    public List<Object> read(String accessToken){

        return null;
    }

    public static List<Object> readFrom(String accessToken) throws IOException {
        try{
            byte[] response = getMessageByteArray(accessToken);

            //System.out.println(response.toString());

            return null;
        } catch(IOException ioException){
            throw new EmailReaderUIException("unable to read email: "+ ioException.getMessage());
        }
    }

    private static byte[] getMessageByteArray(String accessToken) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getMessages = new HttpGet(getMessageRequestURL());
        getMessages.setHeader(AUTHORIZATION, BEARER+accessToken);
        CloseableHttpResponse getMessageResponse = client.execute(getMessages);
        System.out.println(EntityUtils.toString(getMessageResponse.getEntity()));

        return null;
    }

    private static String getMessageRequestURL() {
        StringBuilder requestURLBuilder = new StringBuilder();
        requestURLBuilder.append("https://graph.microsoft.com/v1.0/");
        requestURLBuilder.append(EmailReaderUIConfig.TENANT_ID);
        requestURLBuilder.append("/users/");
        try {
            requestURLBuilder.append(URLEncoder.encode("califiekl@califiekluogmail.onmicrosoft.com", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new EmailReaderUIException("cannot encode user: "+e.getMessage());
        }
        requestURLBuilder.append("/mailFolders/Inbox/messages");
        System.out.println(requestURLBuilder.toString());
        return requestURLBuilder.toString();
    }
}
