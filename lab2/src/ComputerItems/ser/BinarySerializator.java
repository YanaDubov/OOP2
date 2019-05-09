package ComputerItems.ser;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.*;

public class BinarySerializator implements Serializator {
    public  void serialize(Object objects, OutputStream outPut){

        try {
            ObjectOutputStream out = new ObjectOutputStream(outPut);
            out.writeObject(objects);
            out.close();
        } catch (Exception e){
            throw new SerializationException(e);
        }

    }
    public  Object deserialize(InputStream inPut){
        Object obj;
        try {
            ObjectInputStream in = new ObjectInputStream(inPut);
            obj = in.readObject();
            in.close();
        } catch (Exception e){
            throw new SerializationException(e);
        }
        return obj;
    }
}
