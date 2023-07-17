package org.example;

import io.github.califiekl.email.reader.ui.msgraphapi.AuthTokenGetterMSGraphAPIImpl;
import io.github.califiekl.email.reader.ui.msgraphapi.EmailReaderMSGraphAPIImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
       // System.out.println("Hello world!");
        AuthTokenGetterMSGraphAPIImpl reader  = new AuthTokenGetterMSGraphAPIImpl();
        try {
            String authToken = AuthTokenGetterMSGraphAPIImpl.getAuthTokenGraph();
            System.out.println("getAuthToken: "+ authToken);
            EmailReaderMSGraphAPIImpl.readFrom(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(reader.getAuthToken());
    }
}