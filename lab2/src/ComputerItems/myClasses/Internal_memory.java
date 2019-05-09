package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

public class Internal_memory extends Computer_item implements Serializable {
    public Integer size;
    public Integer speed;
    @MyAnnotation( name = "Размер")
    public Integer getSize() {
        return size;
    }
    @MyAnnotation( name = "Размер")
    public void setSize(Integer size) {
        this.size = size;
    }

    @MyAnnotation( name = "Скорость доступа")
    public Integer getSpeed() {
        return speed;
    }
    @MyAnnotation( name = "Скорость доступа")
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
