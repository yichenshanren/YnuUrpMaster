package edu.ynu.sl.status;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/2.
 */
public class Data {

    private ArrayList<String> content = new ArrayList<String>();

    private static Data single = null;

    public static synchronized Data getInstance() {
        if (single == null) {
            single = new Data();
        }
        return single;
    }

    public void updateContent(String text, int index) {
        content.add("> " + text + " ");
        for (int i = index + 1; i < this.content.size(); i++) {
            content.remove(i);
        }
    }

    public String getContent() {
        String text = "";
        for (int i = 0; i < content.size(); i++) {
            text += content.get(i);
        }
        return text;
    }
}
