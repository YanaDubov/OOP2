package ComputerItems.WindowForm;

import java.lang.reflect.Method;

public class GetMyAnnotation {
    public static String getClassAnnotation(Class recClass) {
        if (recClass.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation myAnnotation = (MyAnnotation) recClass.getAnnotation(MyAnnotation.class);
            return myAnnotation.name();
        } else {
            return recClass.getName();
        }
    }

    public static String getFieldAnnotation(Method method) {
        if (method.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            return myAnnotation.name();
        } else {
            return method.getName();
        }
    }

}
