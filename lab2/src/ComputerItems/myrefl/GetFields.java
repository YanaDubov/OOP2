package ComputerItems.myrefl;



import ComputerItems.WindowForm.FirstWindow;
import ComputerItems.WindowForm.GetMyAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class GetFields {
    public static ArrayList<MethodName> field (Class fieldsClass){

        Method[] methods = fieldsClass.getMethods();
        ArrayList<MethodName> fields = new ArrayList<>();
        for (Method item : methods) {
            if(item.getName().startsWith("get") || item.getName().startsWith("is")) {
                String label = item.getName().replace("get","");
                Class <?> type = item.getReturnType();
                String annotation = GetMyAnnotation.getFieldAnnotation(item);
                if (!label.equals( "Class")){
                    fields.add(new MethodName(label,annotation,type,""));
                }
            }
        }
        return fields;
    }

    public static void getFieldsValue (ArrayList<MethodName> fieldsName, Object fieldClass)  {
        try {
            Method method;
            for (MethodName item : fieldsName){
                method =  fieldClass.getClass().getMethod("get" + item.getLabel());
                item.values = method.invoke(fieldClass);
                try {
                    if(!item.values.equals(null))
                        System.out.println(fieldClass + "  " + method + " " + item.values.toString());

                } catch ( NullPointerException e) {

                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
