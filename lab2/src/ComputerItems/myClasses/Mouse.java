package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

@MyAnnotation(name="Мышь")
public class Mouse {
    @MyAnnotation(name = "Тип мыши")
    private Type type;
    private int size;
    private int dpi;
    private String model;

    @MyAnnotation(name = "Название модели")
    public String getModel() {
        return model;
    }

    @MyAnnotation(name = "Название модели")
    public void setModel(String model) {
        this.model = model;
    }

    @MyAnnotation(name = "Тип мыши")
    public enum Type {
        mouse,
        gameMouse,
        trackball,
        touchpad
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @MyAnnotation(name = "Размер")
    public int getSize() {
        return size;
    }

    @MyAnnotation(name = "Размер")
    public void setSize(int size) { this.size = size; }

    @MyAnnotation(name = "DPI")
    public int getDpi() { return dpi; }

    @MyAnnotation(name = "DPI")
    public void setDpi(int dpi) { this.dpi = dpi; }

}