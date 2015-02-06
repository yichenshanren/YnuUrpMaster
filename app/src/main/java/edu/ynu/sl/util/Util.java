package edu.ynu.sl.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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


    public static int getColor() {
        return getColor(new Random());
    }

    public static int getColor(Random r) {

        int randomColor = Color.rgb(r.nextInt(150) + 70, r.nextInt(170) + 70, r.nextInt(170) + 70);
        return randomColor;

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
//    public void showAlter(){
//        new AlertDialog.Builder(getActivity())
//                .setTitle("添加课程")
//                .setView(alterview)
//                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//	                /* User clicked OK so do some stuff */
//                        EditText keming=(EditText)alterview.findViewById(R.id.edit_keming);
//                        EditText daima=(EditText)alterview.findViewById(R.id.edit_daima);
//                        if(daima.getText().toString().equals("")){
//                            Util.showToast(getActivity(), "代码输入不能为空！");
//
//                        }else{
//                            keCeng.add(keming.getText().toString());
//                            daiMa.add(daima.getText().toString());
//
//                            myAdapter.notifyDataSetChanged();
//                        }
//                        ViewGroup p = (ViewGroup) alterview.getParent();
//                        if (p != null) {
//                            p.removeAllViewsInLayout();
//                        }
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//	                /* User clicked cancel so do some stuff */
//                        ViewGroup p = (ViewGroup) alterview.getParent();
//                        if (p != null) {
//                            p.removeAllViewsInLayout();
//                        }
//                    }
//                })
//                .show();
//
//
//
//    }

    public static void showProgressDialog(Context context, String str) {

        dialog3 = ProgressDialog
                .show(context, "", str, false);
    }

    public static void dismiss() {
        dialog3.dismiss();

    }

}
