package io.github.califiekl.email.reader.ui.javamail;

import io.github.califiekl.email.reader.ui.EmailReader;
import io.github.califiekl.email.reader.util.EmailReaderUIException;

import javax.mail.Session;
import javax.mail.Store;
import java.util.List;
import java.util.Properties;

public class EmailReaderJavaMailImpl implements EmailReader {
    static Properties properties = System.getProperties();
    static Session session =Session.getInstance(properties);
    @Override
    public List<Object> read(String accessToken) {
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
            store.connect("outlook.office365.com", "califiekl@outlook.com", accessToken);
        }catch(Exception ex){}
    }
}
