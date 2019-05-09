package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

@MyAnnotation( name = "Клавиатура")
public class Keyboard implements Serializable {
    private String port;
    private String constructor;
    private String purpose;
    private String model;

    @MyAnnotation(name = "Название модели")
    public String getModel() { return model; }
    @MyAnnotation(name = "Название модели")
    public void setModel(String model) { this.model = model; }

    @MyAnnotation( name = "Порт")
    public String getPort() {
        return port;
    }
    @MyAnnotation( name = "Порт")
    public void setPort(String port) {
        this.port = port;
    }

    @MyAnnotation( name = "Конструкция")
    public String getConstructor() {
        return constructor;
    }
    @MyAnnotation( name = "Конструкция")
    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    @MyAnnotation( name = "Цель")
    public String getPurpose() { return purpose; }
    @MyAnnotation( name = "Цель")
    public void setPurpose(String purpose) { this.purpose = purpose; }
}
