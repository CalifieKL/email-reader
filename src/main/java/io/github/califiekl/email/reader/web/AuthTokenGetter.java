package io.github.califiekl.email.reader.web;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.califiekl.email.reader.util.EmailReaderUIException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public abstract class AuthTokenGetter {

    protected final String ACCESS_TOKEN_KEY = "access_token";
    protected ClientApplication clientApp;

    protected abstract String getTokenRequestURL();
    protected abstract String getTokenRequestEncodedBody();

    public AuthTokenGetter(ClientApplication clientApp){
        if(null == clientApp)
            throw new EmailReaderUIException("cannot get token on null client app");
        this.clientApp = clientApp;
    }
    public  String getAuthToken(){
        try{
            String response = getAuthTokenString();
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType type = objectMapper.constructType(
                    objectMapper.getTypeFactory().constructParametricType(Map.class,String.class, String.class));
            Map<String, String> parsed = objectMapper.readValue(response, type);
            return parsed.get(ACCESS_TOKEN_KEY);
        } catch(IOException ioException){
            throw new EmailReaderUIException("unable to get token: "+ ioException.getMessage());
        }

    }

    public String getAuthTokenString() throws IOException {
        String requestURL = getTokenRequestURL();
        String requestEncodedBody = getTokenRequestEncodedBody();
        validateUrlAndEncodedBody(requestURL, requestEncodedBody);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost loginPost = new HttpPost(requestURL);
        loginPost.setEntity(new StringEntity(requestEncodedBody, ContentType.APPLICATION_FORM_URLENCODED));
        loginPost.addHeader(new BasicHeader("cache-control","no-cache"));
        CloseableHttpResponse loginResponse = client.execute(loginPost);
        return EntityUtils.toString(loginResponse.getEntity());
    }

    public ClientApplication getClientApp() {
        return clientApp;
    }

    private void validateUrlAndEncodedBody(String url, String encodedBody){
        if(null == url || null == encodedBody || "".equals(url) || "".equals(encodedBody))
            throw new EmailReaderUIException("null or empty url or encoded body");
    }

}
