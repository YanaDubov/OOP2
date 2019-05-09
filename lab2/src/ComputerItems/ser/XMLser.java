package ComputerItems.ser;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;

public class XMLser implements Serializator {

    public void serialize(Object objects, OutputStream outPut)  {

        XMLEncoder xmlEncoder = new XMLEncoder(outPut);
        xmlEncoder.writeObject(objects);
        xmlEncoder.close();

    }
    public Object deserialize( InputStream inPut){
        Object obj;
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(inPut);
            obj = xmlDecoder.readObject();
            xmlDecoder.close();
        } catch (Exception e) {
            throw new SerializationException(e);
        }
        return obj;
    }
}

