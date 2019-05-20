package ComputerItems.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Plugin {
    void encode(InputStream in, OutputStream out) throws IOException;
    void decode(InputStream in, OutputStream out) throws IOException;
}
