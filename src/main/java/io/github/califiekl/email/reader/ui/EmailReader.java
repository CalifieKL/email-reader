package io.github.califiekl.email.reader.ui;

import java.util.List;

public interface EmailReader {
     List<Object> read(String accessToken);
}
