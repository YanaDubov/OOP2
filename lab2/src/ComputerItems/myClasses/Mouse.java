package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

@MyAnnotation(name="Мышь")
public class Mouse implements Serializable {
    @MyAnnotation(name = "Тип мыши")
    private Type type;
    private Integer size;
    private Integer dpi;
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

    @MyAnnotation(name = "Тип")
    public Type getType() {
        return type;
    }
    @MyAnnotation(name = "Тип")
    public void setType(Type type) {
        this.type = type;
    }

    @MyAnnotation(name = "Размер")
    public Integer getSize() {
        return size;
    }

    @MyAnnotation(name = "Размер")
    public void setSize(Integer size) { this.size = size; }

    @MyAnnotation(name = "DPI")
    public Integer getDpi() { return dpi; }

    @MyAnnotation(name = "DPI")
    public void setDpi(Integer dpi) { this.dpi = dpi; }

}