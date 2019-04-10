package ComputerItems.WindowForm;

import ComputerItems.myrefl.MethodName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GenerateInstance {

    public static Object getNew (Class cl, ArrayList<MethodName> methodName) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object newObj = cl.newInstance();
        String fields = GetMyAnnotation.getClassAnnotation(cl) + " ";
        Method method;
        for (MethodName item : methodName){
            method =  cl.getMethod("set" + item.getLabel(), item.getType());
            System.out.println(newObj + "  " + method);
            if (! item.values.equals("")) {

                if (item.getType().equals(int.class)) {
                    method.invoke(newObj, Integer.parseInt(item.getValues().toString()));// тут item.getValues() строка, надо сделать типа Type
                } else
                    method.invoke(newObj, item.getType().cast(item.getValues()));
                if(item.getLabel().equals("Model")){
                    fields += GetMyAnnotation.getFieldAnnotation(method) + " : " + item.getValues();
                }
            }

        }
        FirstWindow.allObjects.put(fields,newObj);
        return newObj;
    }

}
