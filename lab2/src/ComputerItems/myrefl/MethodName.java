package ComputerItems.myrefl;

public class MethodName {
    private String label;
    private String annotalion;
    public Class<?> type;
    public Object values;

    public String getAnnotalion() {
        return annotalion;
    }

    public void setAnnotalion(String annotalion) {
        this.annotalion = annotalion;
    }



    public MethodName(String label,String annotalion, Class<?> type, Object values) {
        this.label = label;
        this.annotalion = annotalion;
        this.type = type;
        this.values = values;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }

}
