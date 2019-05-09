package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

@MyAnnotation(name = "Контролер ввода-вывода")
public class Controller_IO extends External_Device_Controller implements Serializable {
    Printer printer;
    Mouse mouse;
    @MyAnnotation( name = " Принтер")
    public Printer getPrinter() {
        return printer;
    }
    @MyAnnotation( name = " Принтер")
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    @MyAnnotation( name = " Мышь")
    public Mouse getMouse() {
        return mouse;
    }
    @MyAnnotation( name = " Мышь")
    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}
