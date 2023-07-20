package io.github.califiekl.email.reader.ui;

public interface MailboxConfigurationGetter {
    String getServiceAccountId();
    String getMailFolderUrl();
    default String getReadingOption(){ return ""; }
}
