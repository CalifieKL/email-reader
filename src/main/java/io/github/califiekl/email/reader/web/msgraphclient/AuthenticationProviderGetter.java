package io.github.califiekl.email.reader.web.msgraphclient;

import com.microsoft.graph.authentication.IAuthenticationProvider;

public interface AuthenticationProviderGetter {
    IAuthenticationProvider getProvider();
}
