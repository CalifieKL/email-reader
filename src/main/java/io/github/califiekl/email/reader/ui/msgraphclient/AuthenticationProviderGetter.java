package io.github.califiekl.email.reader.ui.msgraphclient;

import com.microsoft.graph.authentication.IAuthenticationProvider;

public interface AuthenticationProviderGetter {
    IAuthenticationProvider getProvider();
}
