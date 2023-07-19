package io.github.califiekl.email.reader.ui;

import io.github.califiekl.email.reader.util.EmailReaderUIException;

import java.util.List;
import java.util.Map;

public abstract class EmailReader {
     protected final String MESSAGES_DATA_FIELD = "value";
     protected final String SUBJECT = "subject";
     protected final String BODY = "bodyPreview";
     protected AuthTokenGetter tokenGetter;
     protected ServiceAccountGetter accountGetter;

     protected abstract void setServiceAccountGetter();

     public EmailReader(AuthTokenGetter tokenGetter){
          if(null == tokenGetter)
               throw new EmailReaderUIException("cannot create email reader with null token getter");
          this.tokenGetter = tokenGetter;
          setServiceAccountGetter();
     }

     public ServiceAccountGetter getServiceAccountGetter(){
          if(null == accountGetter)
               throw new EmailReaderUIException("no service account specified");
          return accountGetter;
     }
     public abstract List<Map<String, String>> read();
}
