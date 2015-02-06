package edu.ynu.sl.status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ku on 2014/12/27.
 */
public class AppStatus {

    public static final String STATUS_FLOATING_OPEN = "floating_open";
    public static final String STATUS_HOMEBUTTON_OPEN = "homebutton_open";


    private Map<String, Boolean> status;


    private static AppStatus single = null;

    public synchronized static AppStatus getInstance() {
        if (single == null) {
            single = new AppStatus();

        }
        return single;
    }

    public void addStatus(String key, Boolean v) {
        status.put(key, v);
    }

    public Boolean getStatus(String key) {
        return status.get(key);
    }


    private AppStatus() {
        setStatus(new HashMap<String, Boolean>());
    }

    public Map<String, Boolean> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Boolean> status) {
        this.status = status;
        status.put(STATUS_FLOATING_OPEN, false);
        status.put(STATUS_HOMEBUTTON_OPEN, false);
    }

    public void close() {

        if (single != null) {
            single = null;

        }
    }

}
