package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

@MyAnnotation( name = "Кеш память")
public class Cache extends Internal_memory{
    public int level;

    @MyAnnotation( name = "Уровень")
    public int getLevel() {
        return level;
    }
    @MyAnnotation( name = "Уровень")
    public void setLevel(int level) {
        this.level = level;
    }
}
