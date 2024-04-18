package io.github.califiekl.email.reader.web;

public interface MailboxConfigurationGetter {
    String getServiceAccountId();
    String getMailFolderUrl();
    default String getReadingOption(){ return ""; }
}
