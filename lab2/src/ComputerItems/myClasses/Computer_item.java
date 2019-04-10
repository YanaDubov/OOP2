package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

public class Computer_item {

    public String producer;
    public String model;
    @MyAnnotation(name = "Название модели")
    public String getModel() { return model; }
    @MyAnnotation(name = "Название модели")
    public void setModel(String model) { this.model = model; }

    @MyAnnotation(name = "Производитель")
    public String getProducer() {
        return producer;
    }
    @MyAnnotation(name = "Производитель")
    public void setProducer(String producer) {
        this.producer = producer;
    }
}
