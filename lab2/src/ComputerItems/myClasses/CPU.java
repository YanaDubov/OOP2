package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

@MyAnnotation( name = "Процессор")
public class CPU extends Computer_item{
    public int core;
    public int frequency;
    @MyAnnotation( name = "Число ядер")
    public int getCore() {
        return core;
    }
    @MyAnnotation( name = "Число ядер")
    public void setCore(int core) {
        this.core = core;
    }

    @MyAnnotation( name = "Частота")
    public int getFrequency() {
        return frequency;
    }
    @MyAnnotation( name = "Частота")
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
