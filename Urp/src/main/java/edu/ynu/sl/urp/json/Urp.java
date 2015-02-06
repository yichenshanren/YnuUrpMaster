package edu.ynu.sl.urp.json;

/**
 * Created by ku on 2014/12/27.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.squareup.picasso.Picasso;
import edu.ynu.sl.urp.aouth.AOuth;
import edu.ynu.sl.urp.iml.OnGetScheduleListener;
import edu.ynu.sl.urp.util.Msg;
import edu.ynu.sl.urp.util.StreamTool;
import edu.ynu.sl.urp.util.Util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class Urp {
    /*超时*/
    public static final String TIME_OUT = "time out";
    /*无网络*/
    public static final String NO_CONECTION = "no connection";

    public static final String URP_API = "http://202.203.209.96/v5api/api/";

    public static final String URP_LOGIN_DATA_API = "GetLoginCaptchaInfo/null";

    public static final String URP_LOGIN_AUHTCODE_IMG = "http://113.55.0.232/vimgs/";
    public static final String[] CODE = {
            "Code",
            "Name"
    };
    public static final String[] SCHEDULE = {
            "GetSemesterList",
            "School",
            "Major",
            "TeachGrade",
            "CourseProperty",
            "ClassCourse"
    };
    public static final String[] COURSE = {
            "JXBDM",
            "NAME_KC",
            "ZXF",
            "Mon",
            "Tue",
            "Wed",
            "Thu",
            "Fri",
            "Sat",
            "Sun",
            "XKRS",
            "KKRS",
            "JSXM_JS",
            "SKDD",
            "KSRQ",
            "KSSD",
            "BZ",
            "NAME_KCXZ",
            "NAME_KCLX"

    };
    private static final String client_id = "ynumisSite";

    private static final String grant_type = "password";
    public static final String lib = "http://www.lib.ynu.edu.cn/";

    private int msg;
    private OnGetScheduleListener onGetScheduleListener;
    private Bitmap codeBitmap;
    private Context mContext;
    private JsonData jsonData;


    public Urp() {
    }

    public Urp(Context mContext) {
        this.mContext = mContext;
        jsonData = new JsonData();

    }


    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            String[] html;
            Bundle data = null;
            switch (message.what) {

                case Msg.MSG_SCHEDULE_CLASSIFY_SEMESTER://获取学期列表
                    doWithMessage(message, Msg.MSG_SCHEDULE_CLASSIFY_SEMESTER, 0);
                    break;
                case Msg.MSG_SCHEDULE_CLASSIFY_SCHOOL://获取学院列表
                    doWithMessage(message, Msg.MSG_SCHEDULE_CLASSIFY_SCHOOL, 1);
                    break;
                case Msg.MSG_SCHEDULE_CLASSIFY_MAJOR://获取专业列表
                    doWithMessage(message, Msg.MSG_SCHEDULE_CLASSIFY_MAJOR, 2);
                    break;
                case Msg.MSG_SCHEDULE_CLASSIFY_TEACHGRADE://获取专业列表
                    doWithMessage(message, Msg.MSG_SCHEDULE_CLASSIFY_TEACHGRADE, 3);
                    break;
                case Msg.MSG_SCHEDULE_CLASSIFY_COURSEP://获取课程性质列表
                    doWithMessage(message, Msg.MSG_SCHEDULE_CLASSIFY_COURSEP, 4);
                    break;
                case Msg.MSG_SCHEDULE_CLASSIFY_CLASS_COURSE:
                    data = message.getData();
                    html = data.getStringArray("data");
                    if (checkData(html)) {
                        getOnGetScheduleListener().onGetCourseEnd(
                                jsonData.getCourseContent(html[0], COURSE),
                                Msg.MSG_SCHEDULE_CLASSIFY_CLASS_COURSE
                        );
                    }
                    break;
            }
        }
    };


    public void login(final String account, final String password) {

        new Thread() {
            public void run() {
                GetMethod getMethod = null;


                getMethod = new GetMethod(URP_API + URP_LOGIN_DATA_API);
                NameValuePair clientId = new NameValuePair("client_id", client_id);
                NameValuePair gt = new NameValuePair("grant_type", grant_type);
                NameValuePair c = new NameValuePair("username", account);
                NameValuePair p = new NameValuePair("password", password);
            }


        }.start();


    }

    public void getLoginData() {
        new Thread() {
            public void run() {
                GetMethod getMethod = new GetMethod(URP_API + URP_LOGIN_DATA_API);
                http(getMethod, Msg.MSG_SCHEDULE_CLASSIFY_COURSEP);
                //  new JsonData().loginData(data);
            }
        }.start();
    }

    public Bitmap getLoginAuthCodeImg() {

        new Thread() {
            public void run() {
                try {
                    codeBitmap = Picasso
                            .with(mContext)
                            .load(URP_LOGIN_AUHTCODE_IMG + AOuth.getInstance().getTempGuid() + ".png")
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }.start();

        return codeBitmap;
    }


    private void http(final GetMethod mGetMethod, final int msg) {

        final HttpClient client = new HttpClient();
        new Thread() {

            public void run() {

                String[] result = {"", "", "", ""};
                try {

                    Util.print("http(GetMethod mGetMethod");
                    client.executeMethod(mGetMethod);


                    byte[] buff = null;


                    try {
                        buff = StreamTool.readInputStream(mGetMethod.getResponseBodyAsStream());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    result[0] = "no data";
                    if (buff != null)
                        result[0] = new String(buff);
                    Message message = new Message();
                    message.what = msg;
                    Bundle data = new Bundle();
                    data.putStringArray("data", result);
                    message.setData(data);
                    handler.sendMessage(message);
                    Util.print(result[0] + "===============");
                    mGetMethod.releaseConnection();

                } catch (HttpException e) {
                    // TODO Auto-generated catch block
                    result[3] = TIME_OUT;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    result[2] = NO_CONECTION;
                }
            }
        }.start();


    }


    public void getPublicData(String url, int msg) {
        Util.print("getPublicData");
        GetMethod getMethod = new GetMethod(url);
        http(getMethod, msg);

    }

    public void getSemesterList() {
        Util.print("getSemesterList()");
        getPublicData(URP_API + SCHEDULE[0], Msg.MSG_SCHEDULE_CLASSIFY_SEMESTER);

    }

    public void getSchoolList(String school) {
        getPublicData(URP_API + SCHEDULE[1] + school, Msg.MSG_SCHEDULE_CLASSIFY_SCHOOL);
    }

    public void getMajorList(String major) {
        getPublicData(URP_API + SCHEDULE[2] + major, Msg.MSG_SCHEDULE_CLASSIFY_MAJOR);
    }

    public void getTeachGradeList(String teachGrade) {
        getPublicData(URP_API + SCHEDULE[3] + teachGrade, Msg.MSG_SCHEDULE_CLASSIFY_TEACHGRADE);
    }

    public void getCoursePropertry(String coursePropertry) {
        getPublicData(URP_API + SCHEDULE[4] + coursePropertry, Msg.MSG_SCHEDULE_CLASSIFY_COURSEP);
    }

    public void getClassCourse(String classCourse) {
        getPublicData(URP_API + SCHEDULE[5] + classCourse, Msg.MSG_SCHEDULE_CLASSIFY_CLASS_COURSE);
        Util.print(URP_API + SCHEDULE[5] + classCourse);
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public OnGetScheduleListener getOnGetScheduleListener() {
        return onGetScheduleListener;
    }

    public void setOnGetScheduleListener(OnGetScheduleListener onGetScheduleListener) {
        this.onGetScheduleListener = onGetScheduleListener;
    }

    public Boolean checkData(String html[]) {
        if (html[2] == NO_CONECTION || html[3] == TIME_OUT) {
            Util.showToast(mContext, "无网络或网络连接失败");
            return false;
        } else return true;

    }

    private String[] getClassifyArray(int i) {
        return i == 0 ? new String[]{
                "Semester" + CODE[0],
                "Semester" + CODE[1]
        } : new String[]{
                SCHEDULE[i] + CODE[0],
                SCHEDULE[i] + CODE[1]
        };
    }

    private void doWithMessage(Message message, int msg, int index) {
        Bundle data = message.getData();
        String[] html = data.getStringArray("data");
        if (checkData(html)) {
            getOnGetScheduleListener().onGetScheduleEnd(
                    jsonData.getClassify(html[0], getClassifyArray(index)),
                    msg);
        }
    }

}
