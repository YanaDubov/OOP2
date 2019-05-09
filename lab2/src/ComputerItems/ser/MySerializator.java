package ComputerItems.ser;

import ComputerItems.Main;
import ComputerItems.myrefl.GenerateInstance;
import ComputerItems.myrefl.GetFields;
import ComputerItems.myrefl.MethodName;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Vector;

public class MySerializator implements Serializator{
    public  void serialize(Object ob, OutputStream out) {
        try {
            OutputStreamWriter outPut = new OutputStreamWriter(out, "UTF-8");
            Vector objects = (Vector) ob;
            outPut.append('<');
            for (int i = 0; i < objects.size(); i++) {
                outPut.append('{' + "[" + i + "]" + objects.get(i).getClass().getName());
                writeMyObj(objects, objects.get(i), outPut);
                outPut.append("}\n");
            }
            outPut.append('>');
            outPut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMyObj(Vector<Object> objects, Object obj, OutputStreamWriter out) {
        try {
            ArrayList<MethodName> fields;
            fields = GetFields.field(obj.getClass());
            GetFields.getFieldsValue(fields, obj);
            for (MethodName item : fields) {
                if (Main.allClassNames.contains(item.getType())) {
                    out.append(" " + item.getLabel() + ":[" + objects.indexOf(item.values) + "] ");
                } else {
                    out.append(" " + item.getLabel() + ":" + item.values + " ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  Object deserialize(InputStream in){
        Vector<Object> objects = new Vector<>() ;
        try {
            BufferedInputStream inPut = new BufferedInputStream(in);
            inPut.mark(0);
            int st;
            StringBuilder rez;

            while ((inPut.read() != -1 )&&((char)inPut.read() != '>')) {
                if(!(rez = readFromFile(inPut)).equals("")){
                    String index = rez.substring(rez.indexOf("[") + 1, rez.indexOf("]"));
                    String obj = rez.substring(rez.indexOf("]") + 1, rez.indexOf(" "));
                    Object newObj = GenerateInstance.getNewObject(Class.forName(obj));
                    objects.add(Integer.parseInt(index), newObj);
                }
            }
            System.out.println("reset");
            inPut.reset();
            Object obj, fieldObj;
            ArrayList<MethodName> fields;


            while ((inPut.read() != -1 )&&((char)inPut.read() != '>')) {
                if(!(rez = readFromFile(inPut)).equals("")){
                    String index = rez.substring(rez.indexOf("[") + 1, rez.indexOf("]"));
                    obj = objects.get(Integer.parseInt(index));
                    fields = GetFields.field(obj.getClass());
                    GetFields.getFieldsValue(fields, obj);
                    for (MethodName item : fields) {
                        System.out.println(rez);
                        System.out.println(item.getLabel());
                        System.out.println(item.getType());
                        if (rez.indexOf(item.getLabel()) != -1){
                            Integer start = rez.indexOf(item.getLabel())+item.getLabel().length() +1;
                            String rValue = rez.substring(start, rez.indexOf(" ", start));

                            if (Main.allClassNames.contains(item.getType())) {
                                String indexField = rez.substring(rez.indexOf("[",start) + 1, rez.indexOf("]",start));
                                item.setValues(objects.get(Integer.parseInt(indexField)));

                            }else {
                                item.setValues(createPrimitiveObject(item.getType(),rValue));
                            }
                        }
                    }
                    GenerateInstance.setFields(obj,fields);
                }
            }
            inPut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return objects;

    }
    public static Object createPrimitiveObject(Class className, String value) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {

        if (className != String.class) {
            Method valueOf = className.getMethod("valueOf", String.class);
            return valueOf.invoke(null, value);
        } else {
            return value;
        }
    }
    public static  StringBuilder readFromFile(BufferedInputStream in) throws IOException, ClassNotFoundException {
        int st;
        StringBuilder resStr = new StringBuilder();

        while (((char)(st = in.read()) !=  '}' )&&(st != -1 )) {
            resStr.append((char) st);
        }

        return resStr;
    }


}
