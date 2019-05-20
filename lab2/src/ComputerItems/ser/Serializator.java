package ComputerItems.ser;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializator {
    public void serialize(Object o, OutputStream outputStream);

    public Object deserialize(InputStream inputStream);
}

