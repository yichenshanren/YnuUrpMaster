package edu.ynu.sl.urp.ynu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import edu.ynu.sl.urp.iml.OnGetImgLisener;
import edu.ynu.sl.urp.json.Urp;
import edu.ynu.sl.urp.struct.Image;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.urp.util.StreamTool;
import edu.ynu.sl.urp.util.Util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by ku on 2015/1/4.
 */
public class Img {

    //图片主页
    public String indexUrl = "http://photo.ynu.edu.cn/index.php?action=showgal&cat=";
    //图片真实地址
    public static String imgUrl = "http://photo.ynu.edu.cn/";
    public static String imgDown = "http://photo.ynu.edu.cn/download.php?cat=";
    //图片分页
    public String page = "&page=";
    //图片分类
    public static ScheduleClass[] imgClassifyList = {
            new ScheduleClass("7", "会泽院"),
            new ScheduleClass("8", "银杏道"),
            new ScheduleClass("9", "物馆、物理馆"),
            new ScheduleClass("10", "至公堂、贡院、考棚"),
            new ScheduleClass("11", "熊庆来 李广田故居"),
            new ScheduleClass("12", "钟楼<"),
            new ScheduleClass("13", "其他建筑"),
            new ScheduleClass("14", "多彩校园"),
            new ScheduleClass("15", "和谐校园"),
            new ScheduleClass("16", "春满东陆"),
            new ScheduleClass("18", "主页滚动图片"),
            new ScheduleClass("5", "呈贡校区"),
    };

    //
    private OnGetImgLisener onGetImgLisener;

    /**
     * 获取图片的地址
     * *
     */
    public void getImg(String url) {
        final GetMethod getMethod = new GetMethod(indexUrl + url);
        HttpClient httpClient = new HttpClient();

        new Thread() {
            public void run() {
                String[] result = {"", "", "", ""};
                try {
                    //执行get
                    new HttpClient().executeMethod(getMethod);
                    //获取服务器返回内容

                    byte[] buff = null;


                    try {
                        buff = StreamTool.readInputStream(getMethod.getResponseBodyAsStream());
                        result[0] = new String(buff, "gb2312");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Util.print(result[0]);
                    //发送消息
                    Message msg = new Message();
                    msg.what = 0;

                    Bundle data = new Bundle();
                    data.putStringArray("data", result);

                    msg.setData(data);
                    handler.sendMessage(msg);

                } catch (HttpException e) {
                    // TODO Auto-generated catch block
                    result[3] = Urp.TIME_OUT;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    result[2] = Urp.NO_CONECTION;
                }
            }
        }.start();
    }

    /**
     * 处理消息
     */
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            String[] html = message.getData().getStringArray("data");
            getOnGetImgLisener().onGetImgFinish(getImgUrl(html[0]));
        }
    };

    /**
     * 将图片的真实地址从获取到的html中解析出来
     */
    private ArrayList<Image> getImgUrl(String s) {
        ArrayList<Image> imgList = new ArrayList<>();
        if (!s.equals("") && s != null) {
            Document document = Jsoup.parse(s);

            /*获取图片地址及标题-----------------------------------------*/
            Elements img = document.select("img.thumbimage");
           /*获取pic易得download--------------------------------------*/
            Elements pic = document.select("a[href*=pic]");

            if (img.size() > 0 && pic.size() > 0) {
                for (int i = 0; i < img.size() && i < pic.size(); i++) {
                    Image image = new Image();
                    image.setTitle(img.get(i).attr("title")); //获取标题
                    image.setImaUrl(img.get(i).attr("src")); //获取地址
                    /*获取pic----------------*/
                    String[] str = pic.get(i).attr("href").split("&");
                    Util.print(pic.get(i).attr("href"));
                    String picString = "";
                    if (str.length > 2) {
                        picString = str[2];
                    }
                    image.setDownloadUrl(picString);
                    /*----------------------*/
                    imgList.add(image);

                }
            }
            /*--------------------------------------------------------*/

        }
        return imgList;
    }

    public OnGetImgLisener getOnGetImgLisener() {
        return onGetImgLisener;
    }

    public void setOnGetImgLisener(OnGetImgLisener onGetImgLisener) {
        this.onGetImgLisener = onGetImgLisener;
    }
}
