package ComputerItems;

import ComputerItems.WindowForm.FirstWindow;
import ComputerItems.myClasses.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Class<? extends Object>> allClassNames = new ArrayList<>();


    public static void main(String[] args){

        allClassNames.add(Cache.class);
        allClassNames.add(Controller_IO.class);
        allClassNames.add(Controller_Keybord.class);
        allClassNames.add(CPU.class);
        allClassNames.add(Keyboard.class);
        allClassNames.add(Mouse.class);
        allClassNames.add(Printer.class);
        FirstWindow firstWindow=new FirstWindow();
        firstWindow.setVisible(true);
    }
}
