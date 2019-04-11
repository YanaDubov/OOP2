package ComputerItems.WindowForm;

import ComputerItems.myrefl.GenerateInstance;
import ComputerItems.myrefl.GetFields;
import ComputerItems.myrefl.MethodName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class WindowUpdate extends JDialog implements ActionListener {
    private static JDialog formUpdate;
    private static JPanel panelUpdate;
    @Override
    public void actionPerformed(ActionEvent e) {
        formUpdate = new JDialog();
        panelUpdate = new JPanel();
        formUpdate.add(panelUpdate);
        panelUpdate.setLayout(new FlowLayout(20,20,20));
        int row = FirstWindow.table.getSelectedRow();
        String key = (String)FirstWindow.table.getValueAt(row, 0);
        Object current = FirstWindow.allObjects.get(key);
        formUpdate.setTitle(GetMyAnnotation.getClassAnnotation(current.getClass()));
        ArrayList<MethodName> fieldsNames = GetFields.field(current.getClass());
        formUpdate.setSize(450,fieldsNames.size()*100+50);

        GetFields.getFieldsValue(fieldsNames, current);
        ComponentMaker.drawComponent(fieldsNames,panelUpdate);

        JButton buttonChange = new JButton("Изменить");
        panelUpdate.add(buttonChange);

        buttonChange.addActionListener(e12 -> {
            Component[] comps = WindowUpdate.panelUpdate.getComponents();
            ComponentMaker.getComponentsData(fieldsNames, comps, panelUpdate);

            GenerateInstance.setFields(current,fieldsNames);
            formUpdate.dispatchEvent(new WindowEvent(formUpdate,WindowEvent.WINDOW_CLOSING));
            FirstWindow.updateTable();
        });
        formUpdate.setVisible(true);
    }
}

