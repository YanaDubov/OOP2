package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

import java.io.Serializable;

@MyAnnotation( name = "Кеш память")
public class Cache extends Internal_memory implements Serializable {
    public Integer level;

    @MyAnnotation( name = "Уровень")
    public Integer getLevel() {
        return level;
    }
    @MyAnnotation( name = "Уровень")
    public void setLevel(Integer level) {
        this.level = level;
    }
}
