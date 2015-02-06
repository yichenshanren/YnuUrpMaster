package edu.ynu.sl.urp.json;

/**
 * Created by ku on 2014/12/27.
 */

import android.content.Context;
import edu.ynu.sl.urp.aouth.AOuth;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.urp.struct.ScheduleContent;
import edu.ynu.sl.urp.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonData {

    private Context mContext;
    private static final String TempGuid = "TempGuid";
    private static final String ImgGuid = "ImgGuid";


    public JsonData() {
    }

    public JsonData(Context mContext) {
        this.mContext = mContext;
    }


    public void loginData(String[] data) {

        try {
            JSONObject jsonObject = new JSONObject(data[0]);
            AOuth.getInstance().setTempGuid(jsonObject.getString(TempGuid));
            AOuth.getInstance().setImgGuid(jsonObject.getString(ImgGuid));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ArrayList<ScheduleClass> getClassify(String data, String[] key) {
        return publicClassifyData(data, key[0], key[1]);
    }

    private ArrayList<ScheduleClass> publicClassifyData(String data, String key0, String key1) {
        ArrayList<ScheduleClass> dataList = new ArrayList<ScheduleClass>();

        try {
            JSONArray jsonArray = new JSONArray(data);
            Util.print(jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length(); i++) {

                ScheduleClass mClass = new ScheduleClass();
                JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

                mClass.setKey(jsonObject.getString(key0));
                mClass.setValue(jsonObject.getString(key1));
                dataList.add(mClass);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dataList;
    }

    public ArrayList<ScheduleContent> getCourseContent(String data, String[] key) {
        return data(data, key);
    }

    private ArrayList<ScheduleContent> data(String data, String[] key) {
        ArrayList<ScheduleContent> dataList = new ArrayList<ScheduleContent>();

        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {

                ScheduleContent mClass = new ScheduleContent();
                JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
                for (int j = 0; j < key.length; j++) {
                    mClass.add(jsonObject.getString(key[j]));
                }
                Util.print(mClass.toString() + key.length);
                dataList.add(mClass);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataList;
    }
}





