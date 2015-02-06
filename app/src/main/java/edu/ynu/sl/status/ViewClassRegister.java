package edu.ynu.sl.status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ku on 2014/12/27.
 */
public class ViewClassRegister {
    private Map<String, Object> views;
    private static ViewClassRegister single = null;


    private ViewClassRegister() {
        setViews(new HashMap<String, Object>());
    }

    public synchronized static ViewClassRegister getInstance() {
        if (single == null) {
            single = new ViewClassRegister();


        }
        return single;

    }

    public void addView(Object view, String name) {

        views.put(name, view);
    }

    public Object getView(String name) {
        return views.get(name);
    }

    public Map<String, Object> getViews() {
        return views;
    }

    public void setViews(Map<String, Object> views) {
        this.views = views;
    }


    public void close() {
        if (single != null) {
            single = null;
        }
    }


}
