package ComputerItems.WindowForm;

import ComputerItems.Main;
import ComputerItems.myrefl.GetClass;
import ComputerItems.myrefl.GetFields;
import ComputerItems.myrefl.MethodName;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import static ComputerItems.WindowForm.FirstWindow.selectClass;


public class ButtonEventListener extends JDialog implements ActionListener {
    private static JDialog form2;
    private static JPanel panel;


    @Override
    public void actionPerformed(ActionEvent e) {
        form2 = new JDialog();
        panel = new JPanel();
        form2.add(panel);
        panel.setLayout(new FlowLayout(20,20,20));
        form2.setSize(450,550);
        Class <? > cl=(Class<? >) GetClass.getFullClass(selectClass) ;
        form2.setTitle(GetMyAnnotation.getClassAnnotation(cl));

        System.out.println(cl.getName());
        Method[] methods = cl.getMethods();

        ArrayList<MethodName> methodsName = new ArrayList<>();
        for (Method method : methods) {
            if(method.getName().startsWith("get") || method.getName().startsWith("is")) {
                MethodName meth = GetFields.field(method);
                if (!meth.getLabel().equals( "Class")){
                    methodsName.add(meth);
                }
            }
        }

        //Заполнение Jlabel и добавление JTextField
        for(MethodName item : methodsName){
            Component component1 = new JLabel(item.getAnnotalion());
            component1.setPreferredSize(new Dimension(150, 70));
            component1.setSize(30,5);
            component1.setName(item.getLabel());
            panel.add(component1);

            if (item.getType().isEnum()){
                JComboBox enumList = new JComboBox();
                enumList.setPrototypeDisplayValue("12345678901234567890");
                enumList.setEditable(true);
                try {
                    Object [] o=  (item.getType().getEnumConstants());
                    for (Object en: o ) {
                        enumList.addItem(en);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                panel.add(enumList);
                enumList.setEditable(false);


            }else

            {
                if (Main.allClassNames.contains(item.getType())) {
                   // System.out.println("!!!! " + item.getType());
                    JComboBox comboBoxClass = new JComboBox();
                    comboBoxClass.setPrototypeDisplayValue("12345678901234567890");
                    comboBoxClass.setEditable(true);
                    for (Map.Entry<String, Object> myObj : FirstWindow.allObjects.entrySet()) {
                        if (item.getType().equals(myObj.getValue().getClass()))
                        comboBoxClass.addItem(myObj.getKey());
                    }
                    panel.add(comboBoxClass);
                    comboBoxClass.setEditable(false);


                } else {
                    Component component2 = new JTextField("", 17);
                    component2.setName(item.getLabel());
                    panel.add(component2);
                    System.out.printf("Key: %s  Value: %s \n", item.getLabel(), item.getType());
                }
            }
        }

        JButton button = new JButton("Добавить");
        panel.add(button);
        button.addActionListener(e12 -> {
            Component[] comps = ButtonEventListener.panel.getComponents();
            String label = "";
            Object value;
            value = null;
            for (Component comp : comps){
                if (comp instanceof JLabel) {
                    JLabel jLabel= (JLabel) comp;
                    System.out.println("Label: " + jLabel.getName());
                    label = jLabel.getName();
                }
                if (comp instanceof JTextField) {
                    JTextField jTextField= (JTextField) comp;
                    System.out.println("TextField: " + jTextField.getName() + " " + jTextField.getText());
                    value = jTextField.getText();
                }
                if (comp instanceof JComboBox){
                    JComboBox jComboBox =(JComboBox) comp;
                    System.out.println("Combobox: " + jComboBox.getName() + " " + jComboBox.getSelectedItem());
                    value = jComboBox.getSelectedItem();
                }
                if ((label != "") && (value != null)){
                    for (MethodName meth : methodsName) {
                        if (meth.getLabel() == label){
                            if (Main.allClassNames.contains(meth.getType())){
                                meth.setValues(FirstWindow.allObjects.get(value.toString()));

                            }else meth.setValues(value);
                        }
                    }
                    value = null;
                    label = "";
                }
            }
            try {
                System.out.println(GenerateInstance.getNew(cl, methodsName));
                form2.dispatchEvent(new WindowEvent(form2, WindowEvent.WINDOW_CLOSING));
                updateTable();

            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        });


        form2.setVisible(true);
    }

    private static <E extends Enum> E[] getEnumValues(Class<E> enumClass)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = enumClass.getDeclaredField("$VALUES");
        f.setAccessible(true);
        Object o = f.get(null);
        return (E[]) o;
    }
    
    private static void updateTable(){
        deleteAllRows(FirstWindow.model);
        for (Map.Entry<String,Object> item : FirstWindow.allObjects.entrySet()) {
            FirstWindow.model.addRow(new String[]{item.getKey()});
        }
        
    }
    public static void deleteAllRows(final DefaultTableModel model) {
        for( int i = model.getRowCount() - 1; i >= 0; i-- ) {
            model.removeRow(i);
        }
    }

}
