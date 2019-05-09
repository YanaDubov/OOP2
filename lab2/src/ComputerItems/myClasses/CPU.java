package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

@MyAnnotation( name = "Процессор")
public class CPU extends Computer_item implements Serializable {
    public Integer core;
    public Integer frequency;
    @MyAnnotation( name = "Число ядер")
    public Integer getCore() {
        return core;
    }
    @MyAnnotation( name = "Число ядер")
    public void setCore(Integer core) {
        this.core = core;
    }

    @MyAnnotation( name = "Частота")
    public Integer getFrequency() {
        return frequency;
    }
    @MyAnnotation( name = "Частота")
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
