package ComputerItems.WindowForm;


import ComputerItems.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FirstWindow  extends JFrame {
    private JButton addButton = new JButton("Добавить");
    private JButton deleteButton = new JButton("Удалить");
    private JComboBox classes = new JComboBox();
    private JTable table;
    public static DefaultTableModel model;
    public static Map<String,Object> allObjects = new HashMap<>();
    public static String selectClass="Cache";

    public FirstWindow() throws HeadlessException {
        super("Лаба2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        classes.setEditable(true);
        for (Class o: Main.allClassNames) {
            classes.addItem(GetMyAnnotation.getClassAnnotation(o));

        }
        panel.add(classes);
        classes.setEditable(false);

        selectClass ="Cache";
        classes.addActionListener(e ->{
            selectClass=(String) classes.getSelectedItem();
            for (Class o: Main.allClassNames) {

                if( o.isAnnotationPresent(MyAnnotation.class)) {
                    MyAnnotation myAnnotation =(MyAnnotation) o.getAnnotation(MyAnnotation.class);
                    if( myAnnotation.name().equals(selectClass)) {
                        String[] arr = o.toString().split("\\.");
                        selectClass = arr[arr.length - 1];
                    }
                }
            }
        });



        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return true;
            }
        };

        model.addColumn("");
        JScrollPane scrollPane = new JScrollPane();
        table = new JTable(model);
        scrollPane.setViewportView(table);

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ButtonEventListener());
        setContentPane(panel);
        setSize(500, 500);
    }
}
