
import ComputerItems.plugin.Plugin;

import java.util.Arrays;
import java.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Base64Plugin implements Plugin {

    public void encode(InputStream in, OutputStream out) throws IOException {

        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while (( len = in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            byte[] buff = Arrays.copyOfRange(buffer,0,len);

            buff = Base64.getEncoder().encode(buff);
            out.write(buff, 0, buff.length);
        }
        out.close();

    }
    public void decode(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len= in.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            byte[] buff = Arrays.copyOfRange(buffer,0,len);
            buff = Base64.getDecoder().decode(buff);
            out.write(buff, 0, buff.length);
        }
        out.close();

    }
}
