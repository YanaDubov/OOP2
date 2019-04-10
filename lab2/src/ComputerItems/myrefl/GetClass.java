package ComputerItems.myrefl;


import ComputerItems.Main;

public class GetClass {

    public static Object  getFullClass (String nameClass) {
        Object o= new Object();
        for (Class<? extends Object> c : Main.allClassNames) {
            String [] arr=c.toString().split("\\.");
            if (arr[arr.length-1].equals(nameClass))
                o=c;

        }
        return  o;

    }
}
