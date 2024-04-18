package io.github.califiekl.email.reader.web.msgraphclient;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import io.github.califiekl.email.reader.web.EmailReaderUIConfig.CustomApplication;
import io.github.califiekl.email.reader.util.EmailReaderCoreException;

public class GraphServiceClientGetter {

    public static GraphServiceClient getClient(CustomApplication app){
        IAuthenticationProvider authenticationProvider = ClientCredentialsAuthenticationProviderFactory.getProvider(app);
        if (null == authenticationProvider)
            throw new EmailReaderCoreException("cannot get GraphServiceClient with null IAuthenticationProvider");
        return GraphServiceClient
                .builder()
                .authenticationProvider(authenticationProvider)
                .buildClient();
    }
}
