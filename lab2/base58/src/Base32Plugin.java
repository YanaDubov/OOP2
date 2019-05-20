
import ComputerItems.plugin.Plugin;
import com.tarandrus.base32.Base32;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class Base32Plugin implements Plugin {

    public void encode(InputStream in, OutputStream out) throws IOException {

        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            byte[] buff = Arrays.copyOfRange(buffer,0,len);
            buff = (new Base32()).encode(buff).getBytes();
            out.write(buff, 0, buff.length);
        }
        out.close();

    }
    public void decode(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            byte[] buff = Arrays.copyOfRange(buffer,0,len);
            String s = new String(buff);
            buff = (new Base32()).decode(s);
            out.write(buff, 0, buff.length);
        }
        out.close();

    }
}
