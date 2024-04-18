package io.github.califiekl.email.reader.web;

import io.github.califiekl.email.reader.util.EmailReaderUIException;

import java.util.List;
import java.util.Map;

public abstract class EmailReader {
     protected final String MESSAGES_DATA_FIELD = "value";

     protected enum MessageGlossary {
          SUBJECT("subject"), BODY("bodyPreview"),
          SENDER("sender"), FROM("from");
          private String fieldName;
          private MessageGlossary(String fieldName){
               this.fieldName = fieldName;
          }
          public String getFieldName(){ return fieldName; }
     }
     protected AuthTokenGetter tokenGetter;
     protected MailboxConfigurationGetter mailboxConfigurationGetter;

     protected abstract void setServiceAccountGetter();

     public EmailReader(AuthTokenGetter tokenGetter){
          if(null == tokenGetter)
               throw new EmailReaderUIException("cannot create email reader with null token getter");
          this.tokenGetter = tokenGetter;
          setServiceAccountGetter();
     }

     public MailboxConfigurationGetter getServiceAccountGetter(){
          if(null == mailboxConfigurationGetter)
               throw new EmailReaderUIException("no service account specified");
          return mailboxConfigurationGetter;
     }
     public abstract List<Map<String, String>> read();
}
