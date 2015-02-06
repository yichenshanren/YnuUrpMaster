package edu.ynu.sl.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.hub.Hub;
import edu.ynu.sl.hub.Msg;
import edu.ynu.sl.status.CircularProgreeBar;
import edu.ynu.sl.status.Data;
import edu.ynu.sl.ui.adapter.ImgAdapter;
import edu.ynu.sl.ui.view.ImgViewPager;
import edu.ynu.sl.ui.widget.NoScrollListView;
import edu.ynu.sl.urp.iml.OnGetImgLisener;
import edu.ynu.sl.urp.struct.Image;
import edu.ynu.sl.urp.ynu.Img;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/4.
 */
public class ImgFragment extends Fragment implements AdapterView.OnItemClickListener, OnGetImgLisener {

    @InjectView(R.id.frame_container)
    LinearLayout container; //布局容器
    private View progressBar;

    private NoScrollListView imgClassifyList; //显示图片列表的listView
    private ImgAdapter allAdapter; //显示本部和呈贡分类的适配器
    private ImgAdapter classifyAdapter; //显示本部分类的适配器
    private ImgAdapter itemAdapter; //显示详细图片列表的适配器

    private int clickType; //判断当前添加的哪个适配器
    private static final int CLICK_ALL = 0; //allAdapter
    private static final int CLICK_CLASSIFY = 1; //classifyAdapter
    private static final int CLICK_ITEM = 2; //itemAdapter

    private ArrayList<Image> imglist;
    private String cat;

    private Img img; //处理后台数据，从服务器获取图片真实地址

    private String[] myImg = { //缺省图片，为了方便：（ 可以自己从服务器获取 ）
            "photos/7/med_1379927235-7.jpg",
            "photos/5/med_1386316920-5.jpg",
            "photos/7/med_1317176815-1.jpg",
            "photos/8/thb_1318119548-1.jpg",
            "photos/9/thb_1317025794-1.jpg",
            "photos/10/thb_1317025744-1.jpg",
            "photos/11/thb_1318044142-1.jpg",
            "photos/12/thb_1318207117-1.jpg",
            "photos/13/thb_1317083058-1.jpg",
            "photos/14/thb_1317194106-1.jpg",
            "photos/15/thb_1317176834-1.jpg",
            "photos/16/thb_1330306706-16.jpg",
            "photos/18/med_1382665937-18.jpg"
    };
    private boolean listIsAdd = false;//判断list是否已经添加在容器中
    private Boolean progressBarIsAdd = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        //注入视图
        ButterKnife.inject(this, view);
        //设置容器的背景颜色为灰色，以与图片item区分
        container.setBackgroundColor(Color.rgb(231, 231, 231));
        //初始listView
        imgClassifyList = new NoScrollListView(getActivity());
        //绑定lisiView点击事件
        imgClassifyList.setOnItemClickListener(this);
        //在容器中添加list
        addList();

        //
        img = new Img();
        img.setOnGetImgLisener(this); //必须实现

        initAllImgAdapter();
        setAllImgAdapter();
        initClassifyAdapter();
        initItemAdapter();

        initProgressBar();
    }

    private void initProgressBar() {
        progressBar = CircularProgreeBar.getInstance().getView();
         /*设置progress的文字*/
        CircularProgreeBar.getInstance().setProgressText(CircularProgreeBar.LOAD_TEXT);
        /*启动progressbar*/
        CircularProgreeBar.getInstance().start();
    }


    private void initItemAdapter() {
        //本部和呈贡的分类的适配器
        itemAdapter = new ImgAdapter(getActivity(), null);
    }

    private void initClassifyAdapter() {
        //将缺省的校本部的分类名称和图片地址注入适配器
        ArrayList<Image> list = new ArrayList<Image>();
        for (int i = 0; i < Img.imgClassifyList.length - 1; i++) {
            list.add(new Image(Img.imgClassifyList[i].getValue(), myImg[i + 2]));
        }
        classifyAdapter = new ImgAdapter(getActivity(), list);
    }

    private void setAllImgAdapter() {
        imgClassifyList.setAdapter(allAdapter);
        clickType = CLICK_ALL;
    }

    private void setClassifyAdapter() {
        imgClassifyList.setAdapter(classifyAdapter);
        clickType = CLICK_CLASSIFY;
    }

    private void setItemAdapter(ArrayList<Image> urlList) {

        removeProgressBar();
        addList();
        itemAdapter.addAll(urlList);

        imgClassifyList.setAdapter(itemAdapter);
        clickType = CLICK_ITEM;
    }

    private void initAllImgAdapter() {
        ArrayList<Image> list = new ArrayList<Image>();
        list.add(new Image("校本部", myImg[0]));
        list.add(new Image("呈贡校区", myImg[1]));
        allAdapter = new ImgAdapter(getActivity(), list);
    }

    public void addList() {
        if (!listIsAdd) {
            container.addView(imgClassifyList, 0);
            listIsAdd = true;
        }
    }

    public void removeList() {
        if (listIsAdd) {
            container.removeView(imgClassifyList);
            listIsAdd = false;
        }
    }

    public void addProgressBar() {
        if (!progressBarIsAdd) {
            container.addView(progressBar, 0);
            progressBarIsAdd = true;
        }

    }

    public void removeProgressBar() {
        if (progressBarIsAdd) {
            container.removeView(progressBar);
            progressBarIsAdd = false;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (clickType) {
            case CLICK_ALL:
                if (position == 0) {
                    setClassifyAdapter();
                    Data.getInstance().updateContent("校本部", 0);
                    Hub.getInstance().sendMessage(Msg.MESSAGE_SCHEDULE_CONTENT);
                } else {
                    img.getImg("5");
                    cat = "5";
                    removeList();
                    addProgressBar();
                    Data.getInstance().updateContent("呈贡", 0);
                    Hub.getInstance().sendMessage(Msg.MESSAGE_SCHEDULE_CONTENT);
                }
                Hub.getInstance().sendMessage(Msg.MESSAGE_AFTERVIEW_SUBTITLE_BACK);
                break;
            case CLICK_CLASSIFY:
                img.getImg(Img.imgClassifyList[position].getKey());
                cat = Img.imgClassifyList[position].getKey();
                removeList();
                addProgressBar();
                Data.getInstance().updateContent(Img.imgClassifyList[position].getValue(), 1);
                Hub.getInstance().sendMessage(Msg.MESSAGE_SCHEDULE_CONTENT);
                break;
            case CLICK_ITEM:
                new ImgViewPager(getActivity(), imglist, cat, position).show();
                break;
        }
    }


    @Override
    public void onGetImgFinish(ArrayList<Image> imgUrl) {
        imglist = imgUrl;
        setItemAdapter(imgUrl);
    }
}
