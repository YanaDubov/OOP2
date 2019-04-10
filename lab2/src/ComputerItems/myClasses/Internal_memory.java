package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

public class Internal_memory extends Computer_item {
    public int size;
    public int speed;
    @MyAnnotation( name = "Размер")
    public int getSize() {
        return size;
    }
    @MyAnnotation( name = "Размер")
    public void setSize(int size) {
        this.size = size;
    }

    @MyAnnotation( name = "Скорость доступа")
    public int getSpeed() {
        return speed;
    }
    @MyAnnotation( name = "Скорость доступа")
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
