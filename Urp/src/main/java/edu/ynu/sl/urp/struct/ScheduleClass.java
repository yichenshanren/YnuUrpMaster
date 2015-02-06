package edu.ynu.sl.urp.struct;

public class ScheduleClass {
    private String key;
    private String value;

    public ScheduleClass() {
        setKey("");
        setValue("");
    }

    public ScheduleClass(String value) {
        setKey("");
        setValue(value);
    }

    public ScheduleClass(String key, String value) {
        setKey(key);
        setValue(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
