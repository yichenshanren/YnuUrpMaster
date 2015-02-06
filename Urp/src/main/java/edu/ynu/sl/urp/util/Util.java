package edu.ynu.sl.urp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by ku on 2014/12/27.
 */
public class Util {
    public static Boolean flag = false;
    static ProgressDialog dialog3;

    public static void showToast(Context context, String neirong) {
        Toast toast = Toast.makeText(context.getApplicationContext(),
                neirong, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();


    }

    public static void print(String str) {
        System.out.println(str);
    }


    public static Boolean draw() {
        if ((int) (Math.random() * 100) == 1) {
            return true;
        }
        return false;
    }


    public static int getRedColor() {
        Random r = new Random();
        return Color.rgb(r.nextInt(50) + 200, r.nextInt(170), r.nextInt(170));
    }

    public static int getGreenColor() {
        Random r = new Random();
        return Color.rgb(r.nextInt(100) + 50, r.nextInt(50) + 200, r.nextInt(100) + 50);
    }

    public static int getColor(Random r) {

        int randomColor = Color.rgb(r.nextInt(130) + 100, r.nextInt(100) + 130, r.nextInt(100) + 130);
        return randomColor;

    }

    public static int getColor() {
        return getColor(new Random());
    }

    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//璁剧疆鏃ユ湡鏍煎紡
        String time = df.format(new Date());

        return time;
    }

    public static void showAlert(Context context, String title, int i, final Handler handler) {
        String btn1 = "";
        String btn2 = "";
        if (i == 0) {
            btn1 = "重试";
            btn2 = "取消";
        } else {
            btn1 = "是";
            btn2 = "否";

        }


    }

    public static void showProgressDialog(Context context, String str) {

        dialog3 = ProgressDialog
                .show(context, "", str, false);
    }

    public static void dismiss() {
        dialog3.dismiss();

    }

    public static String getClassify(String courseProperty) {
        String classify = "";
        switch (courseProperty.substring(0, 1)) {
            case "人":
                classify = "人文";
                break;
            case "社":
                classify = "社科";
                break;
            case "体":
                classify = "体技";
                break;
            case "外":
                classify = "外语";
                break;
            case "自":
                classify = "自然";
                break;
            case "艺":
                classify = "艺术";
                break;
            default:
                classify = courseProperty;
                break;
        }

        return classify;
    }

    public static String getCourseStatus(String courseSelected, String courseCount) {
        if (isInteger(courseCount) && isInteger(courseSelected)) {
            int selected = Integer.parseInt(courseSelected);
            int count = Integer.parseInt(courseCount);
            return count - selected > 0 ? "剩" : "满";
        }
        return "剩";
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getCourseTime(ArrayList<String> courseTime) {
        String time = "";
        for (int i = 0; i < courseTime.size(); i++) {
            if (!courseTime.get(i).equals("")) {
                time += getWeek(i) + courseTime.get(i);
            }
        }
        return time;
    }

    private static String getWeek(int i) {
        String week = "";
        switch (i) {
            case 0:
                week = "周一";
                break;
            case 1:
                week = "周二";
                break;
            case 2:
                week = "周三";
                break;
            case 3:
                week = "周四";
                break;
            case 4:
                week = "周五";
                break;
            case 5:
                week = "周六";
                break;
            case 6:
                week = "周日";
                break;
        }
        return week;
    }

    public static String getCourseRoom(String courseRoom) {
        if (courseRoom.equals("") || courseRoom == null) {
            return "";
        } else {
            return courseRoom.substring(0, 1);
        }

    }
}
