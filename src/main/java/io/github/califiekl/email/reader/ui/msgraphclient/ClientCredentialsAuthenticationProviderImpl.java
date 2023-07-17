package io.github.califiekl.email.reader.ui.msgraphclient;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import io.github.califiekl.email.reader.ui.EmailReaderUIConfig;
import io.github.califiekl.email.reader.util.EmailReaderCoreException;

import java.util.Arrays;
import java.util.List;

public class ClientCredentialsAuthenticationProviderImpl implements AuthenticationProviderGetter {
    final String clientId = EmailReaderUIConfig.CLIENT_ID;
    final String tenantId = EmailReaderUIConfig.TENANT_ID;
    final String clientSecret = EmailReaderUIConfig.CLIENT_SECRET;

    final List<String> scopes = Arrays.asList(EmailReaderUIConfig.MS_GRAPH_SCOPE);

    final ClientSecretCredential credential = new ClientSecretCredentialBuilder()
            .clientId(clientId).tenantId(tenantId).clientSecret(clientSecret).build();

    // The client credentials flow requires that you request the
// /.default scope, and pre-configure your permissions on the
// app registration in Azure. An administrator must grant consent
// to those permissions beforehand.





    //final GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

    @Override
    public IAuthenticationProvider getProvider(){
        if (null == scopes || null == credential) {
            throw new EmailReaderCoreException("Unexpected error: null scope or credential");
        }
        return new TokenCredentialAuthProvider(scopes, credential);
    }
}
