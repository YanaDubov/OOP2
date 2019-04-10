package ComputerItems.WindowForm;

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
import java.util.ArrayList;
import java.util.Map;

import static ComputerItems.WindowForm.FirstWindow.selectClass;


public class WindowAdd extends JDialog implements ActionListener {
    private static JDialog form2;
    private static JPanel panel;


    @Override
    public void actionPerformed(ActionEvent e) {
        form2 = new JDialog();
        panel = new JPanel();
        form2.add(panel);
        panel.setLayout(new FlowLayout(20,20,20));

        Class <? > cl=(Class<? >) GetClass.getFullClass(selectClass) ;
        form2.setTitle(GetMyAnnotation.getClassAnnotation(cl));

        ArrayList<MethodName> fieldsName = GetFields.field(cl);
        form2.setSize(450,fieldsName.size()*100+50);
        ComponentMaker.drawComponent(fieldsName,panel);

        JButton button = new JButton("Добавить");
        panel.add(button);
        button.addActionListener(e12 -> {
            Component[] comps = WindowAdd.panel.getComponents();
            ComponentMaker.getComponentsData(fieldsName, comps, panel);

            Object newObject = GenerateInstance.getNewObject(cl);
            assert newObject != null;
            GenerateInstance.setFields(newObject,fieldsName);
            form2.dispatchEvent(new WindowEvent(form2, WindowEvent.WINDOW_CLOSING));
            FirstWindow.updateTable();
        });


        form2.setVisible(true);
    }



}
