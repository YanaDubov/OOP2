package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

@MyAnnotation( name = "Принтер")
public class Printer implements Serializable {

    private Kind kind;
    private String color;
    private String model;
    public enum Kind{
        jet,
        laser,
        matrix,
        sublimation,
        thermal
    }
    @MyAnnotation(name = "Название модели")
    public String getModel() { return model; }
    @MyAnnotation(name = "Название модели")
    public void setModel(String model) { this.model = model; }

    @MyAnnotation( name = "Тип")
    public Kind getKind() {
        return kind;
    }
    @MyAnnotation( name = "Тип")
    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @MyAnnotation( name = "Цвет")
    public String getColor() {
        return color;
    }
    @MyAnnotation( name = "Цвет")
    public void setColor(String color) {
        this.color = color;
    }
}
