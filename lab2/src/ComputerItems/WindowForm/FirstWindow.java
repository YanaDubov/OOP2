package ComputerItems.WindowForm;


import ComputerItems.Main;
import ComputerItems.myClasses.*;
import ComputerItems.myrefl.DeleteObj;
import ComputerItems.myrefl.GenerateInstance;
import ComputerItems.myrefl.GetFields;
import ComputerItems.myrefl.MethodName;
import ComputerItems.plugin.Plugin;
import ComputerItems.plugin.PluginWithInformation;
import ComputerItems.ser.BinarySerializator;
import ComputerItems.ser.MySerializator;
import ComputerItems.ser.Serializator;
import ComputerItems.ser.XMLSerializator;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class FirstWindow extends JFrame {
    private JComboBox classes = new JComboBox();
    public static JTable table;
    public static DefaultTableModel model;
    public static Map<String, Object> allObjects = new HashMap<>();
    public static String selectClass = "Cache";
    public static JPanel panel = new JPanel();
    public static Map<String,Class<? extends Serializator>> serializers = new HashMap<>();
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

        serializers.put("txt",MySerializator.class);
        serializers.put("bin",BinarySerializator.class);
        serializers.put("xml",XMLSerializator.class);


        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();
        Map<String,PluginWithInformation> plugins = new HashMap<>();
        PluginWithInformation pluginWithInformationEmply = new PluginWithInformation();
        plugins.put(pluginWithInformationEmply.pluginName,pluginWithInformationEmply);
        for (File file : listOfFiles) {
            if (file.isFile()) {
                PluginWithInformation pluginWithInformation = new PluginWithInformation(file);
                plugins.put(pluginWithInformation.pluginName,pluginWithInformation);
            }
        }


        saveButton.addActionListener(e -> {
            try{
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            for (Map.Entry<String, Class<?extends Serializator>> item : serializers.entrySet()) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter (item.getKey(),item.getKey());
                fileChooser.addChoosableFileFilter(filter);
            }



            JComboBox comboBox = new JComboBox();
            comboBox.setEditable(true);
            for (Map.Entry<String,PluginWithInformation> item : plugins.entrySet()) {
                comboBox.addItem(item.getKey());
            }
            fileChooser.add(comboBox);

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                FileFilter extension = fileChooser.getFileFilter();

                Serializator serializator = serializers.get(extension.getDescription()).newInstance();
                PluginWithInformation plugin = plugins.get(comboBox.getSelectedItem());

                File fileToSave = new File(fileChooser.getSelectedFile() +"."+ (plugin.pluginExtention.equals("") ? extension.getDescription() : plugin.pluginExtention));



                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                Vector<Object> objects = new Vector<>();
                for (Map.Entry<String, Object> item : FirstWindow.allObjects.entrySet()) {
                    objects.add(item.getValue());
                }
                allObjects.clear();
                serialize(serializator,plugin.plugin,fileToSave,objects);
                updateTable();
            }
            } catch (IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
        });


        openButton.addActionListener(e -> {
            try {
                JFrame parentFrame = new JFrame();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to read");


                for (Map.Entry<String, Class<? extends Serializator>> item : serializers.entrySet()) {
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(item.getKey(), "b32", "b64", item.getKey());
                    fileChooser.addChoosableFileFilter(filter);
                }

                int userSelection = fileChooser.showOpenDialog(parentFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToOpen = fileChooser.getSelectedFile();
                    FileFilter extension = fileChooser.getFileFilter();

                    Serializator serializator = serializers.get(extension.getDescription()).newInstance();
                    String ext = fileToOpen.getName().substring(fileToOpen.getName().indexOf(".") + 1);
                    Plugin plugin = null;
                    for (Map.Entry<String, PluginWithInformation> item : plugins.entrySet()) {
                        if (item.getValue().pluginExtention.equals(ext)) {
                            plugin = item.getValue().plugin;
                        }
                    }
                    System.out.println("Open file: " + fileToOpen.getAbsolutePath());

                    ArrayList<MethodName> fields;
                    Vector<Object> objects = deserialize(serializator, plugin, fileToOpen);
                    allObjects.clear();
                    for (Object item : objects) {
                        fields = GetFields.field(item.getClass());
                        GetFields.getFieldsValue(fields, item);
                        GenerateInstance.setFields(item, fields);

                    }
                    updateTable();
                }
            } catch (IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
        });
        setContentPane(panel);
        setSize(550, 500);
        exClass();


    }

    public static void serialize(Serializator serializator, Plugin plugin, File file, Vector<Object> objects ){
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

            if(plugin != null){
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                serializator.serialize(objects, byteOut);
                plugin.encode(new ByteArrayInputStream(byteOut.toByteArray()),out);
            }else {
                serializator.serialize(objects, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Vector<Object> deserialize(Serializator serializator, Plugin plugin, File file ){
        try {

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            if(plugin != null){
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                plugin.decode(in,byteOut);

               return (Vector)serializator.deserialize(new ByteArrayInputStream(byteOut.toByteArray()));

            }else {
               return (Vector)serializator.deserialize(in);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
