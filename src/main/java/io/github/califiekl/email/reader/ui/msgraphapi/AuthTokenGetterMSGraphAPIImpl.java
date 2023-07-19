package io.github.califiekl.email.reader.ui.msgraphapi;

import io.github.califiekl.email.reader.ui.AuthTokenGetter;
import io.github.califiekl.email.reader.ui.ClientApplication;
import io.github.califiekl.email.reader.ui.EmailReaderUIConfig;

public class AuthTokenGetterMSGraphAPIImpl extends AuthTokenGetter {

    public AuthTokenGetterMSGraphAPIImpl(ClientApplication clientApp){
        super(clientApp);
    }
    @Override
    protected String getTokenRequestURL(){
        StringBuilder requestURLBuilder = new StringBuilder();
        requestURLBuilder.append("https://login.microsoftonline.com/");
        requestURLBuilder.append(clientApp.getTenantId());
        requestURLBuilder.append("/oauth2/v2.0/token");
        return requestURLBuilder.toString();
    }

    @Override
    protected String getTokenRequestEncodedBody(){
        StringBuilder encodedBodyBuilder = new StringBuilder();
        encodedBodyBuilder.append("client_id=");
        encodedBodyBuilder.append(clientApp.getClientId());
        encodedBodyBuilder.append("&scope=");
        encodedBodyBuilder.append(EmailReaderUIConfig.MS_GRAPH_SCOPE);
        encodedBodyBuilder.append("&client_secret=");
        encodedBodyBuilder.append(clientApp.getClientSecret());
        encodedBodyBuilder.append("&grant_type=client_credentials");
        return encodedBodyBuilder.toString();
    }
}
