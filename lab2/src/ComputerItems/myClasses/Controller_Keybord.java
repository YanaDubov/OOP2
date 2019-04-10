package ComputerItems.myClasses;


import ComputerItems.WindowForm.MyAnnotation;

@MyAnnotation( name = "Контроллер клавиатуры")
public class Controller_Keybord extends External_Device_Controller{

    Keyboard keyboard;
    @MyAnnotation( name = "Клавиатура")
    public Keyboard getKeyboard() {
        return keyboard;
    }
    @MyAnnotation( name = "Клавиатура")
    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }
}
