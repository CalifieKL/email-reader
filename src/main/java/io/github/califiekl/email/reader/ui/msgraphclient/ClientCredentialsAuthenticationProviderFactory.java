package io.github.califiekl.email.reader.ui.msgraphclient;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import io.github.califiekl.email.reader.ui.EmailReaderUIConfig.CustomApplication;

import java.util.HashMap;
import java.util.Map;

public class ClientCredentialsAuthenticationProviderFactory{
    private static Map<CustomApplication, AuthenticationProviderGetter> authenticationProviderGetterMap;
    static {
        authenticationProviderGetterMap = new HashMap<>();
        authenticationProviderGetterMap.put(CustomApplication.EMAIL_READER, new ClientCredentialsAuthenticationProviderImpl());
    }

    public static IAuthenticationProvider getProvider(CustomApplication app){
        AuthenticationProviderGetter authenticationProviderGetterImpl = authenticationProviderGetterMap.get(app);
            if(null == authenticationProviderGetterImpl) return null;
        return authenticationProviderGetterImpl.getProvider();
    }
}
