package ComputerItems.WindowForm;

import ComputerItems.Main;
import ComputerItems.myrefl.MethodName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

public class ComponentMaker {
    public static void drawComponent(ArrayList<MethodName> mass, JPanel panel) {

        for (MethodName item : mass) {
            Component componentLabel = new JLabel(item.getAnnotalion());
            componentLabel.setPreferredSize(new Dimension(150, 70));
            componentLabel.setSize(30, 5);
            componentLabel.setName(item.getLabel());
            panel.add(componentLabel);

            if (item.getType().isEnum()) {
                JComboBox enumList = new JComboBox();
                enumList.setPrototypeDisplayValue("12345678901234567890");
                enumList.setEditable(true);
                try {
                    Object[] o = (item.getType().getEnumConstants());
                    for (Object en : o) {
                        enumList.addItem(en);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    enumList.setSelectedItem(item.values);
                } catch (NullPointerException e) {
                    enumList.setSelectedIndex(-1);
                }
                panel.add(enumList);
                enumList.setEditable(false);


            } else {
                if (Main.allClassNames.contains(item.getType())) {
                    JComboBox comboBoxClass = new JComboBox();
                    comboBoxClass.setPrototypeDisplayValue("12345678901234567890");
                    comboBoxClass.setEditable(true);
                    for (Map.Entry<String, Object> myObj : FirstWindow.allObjects.entrySet()) {
                        if (item.getType().equals(myObj.getValue().getClass()))
                            comboBoxClass.addItem(myObj.getKey());
                    }
                    try {
                        comboBoxClass.setSelectedItem(FirstWindow.getKeyValue(item.values));
                    } catch (NullPointerException e) {
                        comboBoxClass.setSelectedItem(-1);
                    }
                    panel.add(comboBoxClass);
                    comboBoxClass.setEditable(false);


                } else {
                    Component componentText = new JTextField("", 17);
                    componentText.setName(item.getLabel());
                    try {
                        ((JTextField) componentText).setText(item.values.toString());
                    } catch (NullPointerException e) {
                        ((JTextField) componentText).setText("");
                    }
                    if (item.getType().equals(Integer.class)) {
                        componentText.addKeyListener(new KeyAdapter() {
                            public void keyTyped(KeyEvent e) {
                                char c = e.getKeyChar();
                                if (((c < '0') || (c > '9'))) {
                                    e.consume();
                                }
                            }
                        });
                    }
                    panel.add(componentText);
                }
            }
        }
    }

    public static void getComponentsData(ArrayList<MethodName> mass, Component[] components, JPanel panel) {

        String label = "";
        Object value = null;
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel jLabel = (JLabel) comp;
                label = jLabel.getName();
            }
            if (comp instanceof JTextField) {
                JTextField jTextField = (JTextField) comp;
                value = jTextField.getText();
            }
            if (comp instanceof JComboBox) {
                JComboBox jComboBox = (JComboBox) comp;
                try {
                    value = jComboBox.getSelectedItem().equals("") ? null : jComboBox.getSelectedItem();
                } catch (NullPointerException e) {
                }
            }
            if ((label != "") && (value != null)) {
                for (MethodName meth : mass) {
                    if (meth.getLabel() == label) {
                        if (Main.allClassNames.contains(meth.getType())) {
                            meth.setValues(FirstWindow.allObjects.get(value.toString()));

                        } else meth.setValues(value);
                    }
                }
                value = null;
                label = "";
            }
        }
    }

}
