package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

public class External_Device_Controller extends Computer_item implements Serializable {
    public Standart standart;
    public enum Standart{
        ANSI,
        IEEE,
        ISO,
        etc
    }

    @MyAnnotation( name = "Стандарт")
    public Standart getStandart() {
        return standart;
    }
    @MyAnnotation( name = "Стандарт")
    public void setStandart(Standart standart) {
        this.standart = standart;
    }
}
