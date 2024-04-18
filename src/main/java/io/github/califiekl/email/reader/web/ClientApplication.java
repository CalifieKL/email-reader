package io.github.califiekl.email.reader.web;

import io.github.califiekl.email.reader.util.EmailReaderUIException;

public abstract class ClientApplication {

    protected String clientId;
    protected String tenantId;
    protected String appObjectId;
    protected String clientSecret;
    protected String clientSecretId;

    public ClientApplication(){
        setParameters();
    }

    protected abstract void setParameters();

    public String getClientId(){
        nullCheck(clientId);
        return clientId;
    }
    public String getTenantId(){
        nullCheck(tenantId);
        return tenantId;
    }
    public String getAppObjectI(){
        nullCheck(appObjectId);
        return appObjectId;
    }
    public String getClientSecret(){
        nullCheck(clientSecret);
        return clientSecret;
    }
    public String getClientSecretId(){
        return clientSecretId;
    }

    protected void nullCheck(String fieldValue){
        if (null == fieldValue)
            throw new EmailReaderUIException("client application field is null");
    }
}
