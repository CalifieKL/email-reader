package io.github.califiekl.email.reader.ui.msgraphapi;

import io.github.califiekl.email.reader.ui.AuthTokenGetter;
import io.github.califiekl.email.reader.ui.EmailReaderUIConfig;

public class AuthTokenGetterMSGraphAPIImpl extends AuthTokenGetter {

    @Override
    protected String getTokenRequestURL(){
        StringBuilder requestURLBuilder = new StringBuilder();
        requestURLBuilder.append("https://login.microsoftonline.com/");
        requestURLBuilder.append(EmailReaderUIConfig.TENANT_ID);
        requestURLBuilder.append("/oauth2/v2.0/token");
        return requestURLBuilder.toString();
    }

    @Override
    protected String getTokenRequestEncodedBody(){
        StringBuilder encodedBodyBuilder = new StringBuilder();
        encodedBodyBuilder.append("client_id=");
        encodedBodyBuilder.append(EmailReaderUIConfig.CLIENT_ID);
        encodedBodyBuilder.append("&scope=");
        encodedBodyBuilder.append(EmailReaderUIConfig.MS_GRAPH_SCOPE);
        encodedBodyBuilder.append("&client_secret=");
        encodedBodyBuilder.append(EmailReaderUIConfig.CLIENT_SECRET);
        encodedBodyBuilder.append("&grant_type=client_credentials");
        return encodedBodyBuilder.toString();
    }
}
