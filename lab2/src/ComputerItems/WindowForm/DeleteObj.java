package ComputerItems.WindowForm;

import ComputerItems.myrefl.GenerateInstance;
import ComputerItems.myrefl.GetFields;
import ComputerItems.myrefl.MethodName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class DeleteObj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = FirstWindow.table.getSelectedRow();
        String key = (String) FirstWindow.table.getValueAt(row, 0);
        Object currentObj = FirstWindow.allObjects.get(key);
        ArrayList<MethodName> fieldsNames;
        String[] arr = currentObj.getClass().toString().split("\\.");
        String selectClass = arr[arr.length - 1];
        Object nullObj = null;
        for (Map.Entry<String,Object> item : FirstWindow.allObjects.entrySet()) {

            fieldsNames = GetFields.field(item.getValue().getClass());
            for (MethodName itemMass : fieldsNames) {
                try {
                    if (itemMass.getLabel().equals(selectClass)) {
                        Method method = item.getValue().getClass().getMethod("get" + itemMass.getLabel());
                        itemMass.values = method.invoke(item.getValue());
                        if (itemMass.values.equals(currentObj)){
                            method = item.getValue().getClass().getMethod("set" + itemMass.getLabel(), itemMass.getType());
                            method.invoke(item.getValue(),nullObj);
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
           // GenerateInstance.setFields(item, fieldsNames);
        }
        FirstWindow.allObjects.remove(key,currentObj);
        FirstWindow.updateTable();
    }
}
