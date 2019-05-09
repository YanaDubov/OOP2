package ComputerItems.WindowForm;


import ComputerItems.Main;
import ComputerItems.myClasses.*;
import ComputerItems.myrefl.DeleteObj;
import ComputerItems.myrefl.GenerateInstance;
import ComputerItems.myrefl.GetFields;
import ComputerItems.myrefl.MethodName;
import ComputerItems.ser.BinarySerializator;
import ComputerItems.ser.MySerializator;
import ComputerItems.ser.Serializator;
import ComputerItems.ser.XMLser;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class FirstWindow extends JFrame {
    private JComboBox classes = new JComboBox();
    public static JTable table;
    public static DefaultTableModel model;
    public static Map<String, Object> allObjects = new HashMap<>();
    public static String selectClass = "Cache";
    public static JPanel panel = new JPanel();

    public FirstWindow() throws HeadlessException {
        super("Лаба2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        classes.setEditable(true);
        for (Class o : Main.allClassNames) {
            classes.addItem(GetMyAnnotation.getClassAnnotation(o));
        }
        panel.add(classes);
        classes.setEditable(false);

        selectClass = "Cache";
        classes.addActionListener(e -> {
            selectClass = (String) classes.getSelectedItem();
            for (Class o : Main.allClassNames) {
                if (o.isAnnotationPresent(MyAnnotation.class)) {
                    MyAnnotation myAnnotation = (MyAnnotation) o.getAnnotation(MyAnnotation.class);
                    if (myAnnotation.name().equals(selectClass)) {
                        String[] arr = o.toString().split("\\.");
                        selectClass = arr[arr.length - 1];
                    }
                }
            }
        });

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        model.addColumn("");
        JScrollPane scrollPane = new JScrollPane();
        table = new JTable(model);

        scrollPane.setViewportView(table);

        JButton addButton = new JButton("Добавить");
        panel.add(addButton);
        JButton deleteButton = new JButton("Удалить");
        panel.add(deleteButton);
        JButton updateButton = new JButton("Изменить");
        panel.add(updateButton);
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton saveButton = new JButton("Сохранить");
        panel.add(saveButton);
        JButton openButton = new JButton("Открыть");
        panel.add(openButton);
        addButton.addActionListener(new WindowAdd());
        updateButton.addActionListener(new WindowUpdate());
        deleteButton.addActionListener(new DeleteObj());
        saveButton.addActionListener(e -> {
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                Serializator serializator = getSerializator(fileToSave.getName());
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                Vector<Object> objects = new Vector<>();
                for (Map.Entry<String, Object> item : FirstWindow.allObjects.entrySet()) {
                    objects.add(item.getValue());
                }
                allObjects.clear();
                BufferedOutputStream out = null;
                try {
                    out = new BufferedOutputStream(new FileOutputStream(fileToSave));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                serializator.serialize(objects, out);
                updateTable();
            }
        });
        openButton.addActionListener(e -> {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to read");

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();
                Serializator serializator = getSerializator(fileToOpen.getAbsolutePath());
                System.out.println("Open file: " + fileToOpen.getAbsolutePath());
                BufferedInputStream in = null;
                try {
                    in = new BufferedInputStream(new FileInputStream(fileToOpen));
                } catch (FileNotFoundException exx) {
                    exx.printStackTrace();
                }
                ArrayList<MethodName> fields = new ArrayList<>();
                Vector<Object> objects = (Vector<Object>) serializator.deserialize(in);
                allObjects.clear();
                for ( Object item : objects ) {
                    fields = GetFields.field(item.getClass());
                    GetFields.getFieldsValue(fields,item);
                    GenerateInstance.setFields(item,fields);

                }
                updateTable();
            }
        });
        setContentPane(panel);
        setSize(550, 500);
        exClass();
    }

    public static void updateTable() {
        deleteAllRows(FirstWindow.model);
        for (Map.Entry<String, Object> item : allObjects.entrySet()) {
            FirstWindow.model.addRow(new String[]{item.getKey()});
        }

    }

    private static void deleteAllRows(final DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    static String getKeyValue(Object currObject) {
        for (Map.Entry<String, Object> item : allObjects.entrySet()) {
            if (item.getValue().equals(currObject)) {
                return item.getKey();
            }
        }
        return null;
    }

    private static Serializator getSerializator(String filename) {
        String ext = filename.substring(filename.indexOf(".") + 1);

        if (ext.equals("txt")) {
            return new MySerializator();
        }
        if (ext.equals("bin")) {
            return new BinarySerializator();
        }
        if (ext.equals("xml")) {
            return new XMLser();
        }
        return null;
    }

    private static void exClass() {
        Cache cache = new Cache();
        cache.setLevel(1);
        cache.setModel("85");
        cache.setProducer("IBM");
        cache.setSize(5120);
        cache.setSpeed(10664);
        allObjects.put("Кеш память Название модели : 85", cache);

        Mouse mouse = new Mouse();
        mouse.setModel("Apple");
        mouse.setSize(20);
        mouse.setDpi(45);
        mouse.setType(Mouse.Type.touchpad);
        allObjects.put("Мышь Название модели : Apple", mouse);

        Printer printer = new Printer();
        printer.setColor("black-white");
        printer.setKind(Printer.Kind.laser);
        printer.setModel("LG-9086");
        allObjects.put("Принтер Название модели : LG-9086", printer);

        Controller_IO io = new Controller_IO();
        io.setMouse(mouse);
        io.setPrinter(printer);
        io.setModel("90");
        io.setProducer("APC");
        io.setStandart(External_Device_Controller.Standart.etc);
        allObjects.put("Контролер ввода-вывода Название модели : 90", io);

        updateTable();
    }
}
