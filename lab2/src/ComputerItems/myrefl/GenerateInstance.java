package ComputerItems.myrefl;

import ComputerItems.WindowForm.FirstWindow;
import ComputerItems.WindowForm.GetMyAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class GenerateInstance {
    public static Object getNewObject(Class newClass) {
        try {
            Object newObject = newClass.newInstance();
            return newObject;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setFields(Object newObj, ArrayList<MethodName> methodName) {
        try {
            String fields = GetMyAnnotation.getClassAnnotation(newObj.getClass()) + " ";
            Method method;
            for (MethodName item : methodName) {
                method = newObj.getClass().getMethod("set" + item.getLabel(), item.getType());
                if (item.values != null && !item.values.equals("")) {

                    if (item.getType().equals(int.class)) {
                        method.invoke(newObj, Integer.parseInt(item.getValues().toString()));// тут item.getValues() строка, надо сделать типа Type
                    } else
                        method.invoke(newObj, item.getType().cast(item.getValues()));
                    if (item.getLabel().equals("Model")) {
                        fields += GetMyAnnotation.getFieldAnnotation(method) + " : " + item.getValues();
                    }
                }
            }
            String delKey = "";
            for (Map.Entry<String, Object> item : FirstWindow.allObjects.entrySet()) {
                if (newObj.equals(item.getValue())) {
                    delKey = item.getKey();
                }
            }
            if ((!delKey.equals(""))) {
                FirstWindow.allObjects.remove(delKey);
            }
            FirstWindow.allObjects.put(fields, newObj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
