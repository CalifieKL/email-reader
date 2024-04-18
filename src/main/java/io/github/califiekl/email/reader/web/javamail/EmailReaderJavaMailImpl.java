package io.github.califiekl.email.reader.web.javamail;

import io.github.califiekl.email.reader.web.AuthTokenGetter;
import io.github.califiekl.email.reader.web.EmailReader;
import io.github.califiekl.email.reader.util.EmailReaderUIException;

import javax.mail.Session;
import javax.mail.Store;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class EmailReaderJavaMailImpl extends EmailReader {
    static Properties properties = System.getProperties();
    static Session session =Session.getInstance(properties);

    public EmailReaderJavaMailImpl(AuthTokenGetter tokenGetter){super(tokenGetter);}

    @Override
    protected void setServiceAccountGetter(){

    }
    @Override
    public List<Map<String, String>> read() {
        return null;
    }

    public static void readEmail(String accessToken){
        Store store = null;
        session.setDebug(true);
        properties.setProperty("mail.imap.ssl.enable", "true");
        properties.setProperty("mail.imap.auth.plain.disable","true");
        try{
            store = session.getStore("imap");
        }catch(Exception ex){
            ex.printStackTrace();
            throw new EmailReaderUIException("cannot get store: "+ex.getMessage());
        }
        if(null == store){
            System.out.println("store is null, return");return;
        }
        try{
            store.connect("outlook.office365.com", "mail@test.coms", accessToken);
        }catch(Exception ex){}
    }
}
