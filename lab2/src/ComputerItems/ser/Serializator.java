package ComputerItems.ser;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializator {
    void serialize(Object o, OutputStream outputStream);
    Object deserialize(InputStream inputStream) ;
}

