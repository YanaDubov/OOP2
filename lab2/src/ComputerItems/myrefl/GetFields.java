package ComputerItems.myrefl;



import ComputerItems.WindowForm.GetMyAnnotation;

import java.lang.reflect.Method;

public class GetFields {
    public static MethodName  field (Method method){

        String label = method.getName().replace("get","");
        Class <?> type = method.getReturnType();
        String annotation = GetMyAnnotation.getFieldAnnotation(method);
        MethodName methodName= new MethodName(label,annotation,type,"");
        return methodName;
    }
}
