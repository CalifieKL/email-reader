package org.example;

import io.github.califiekl.email.reader.ui.AuthTokenGetter;
import io.github.califiekl.email.reader.ui.EmailReader;
import io.github.califiekl.email.reader.ui.client.MyApp;
import io.github.califiekl.email.reader.ui.client.SampleApp;
import io.github.califiekl.email.reader.ui.msgraphapi.AuthTokenGetterMSGraphAPIImpl;
import io.github.califiekl.email.reader.ui.msgraphapi.EmailReaderMSGraphAPIImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
       // System.out.println("Hello world!");
        AuthTokenGetter tokenGetter  = new AuthTokenGetterMSGraphAPIImpl(new SampleApp());
        EmailReader reader = new EmailReaderMSGraphAPIImpl(tokenGetter);
        //System.out.println("getAuthToken: "+ authToken);
        reader.read();
        //System.out.println(reader.getAuthToken());
    }
}