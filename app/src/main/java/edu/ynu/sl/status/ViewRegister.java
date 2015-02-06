package edu.ynu.sl.status;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/27.
 */
public class ViewRegister {
    private static final int LOGIN_VIEW = 1;     //登陆view
    private static final int CONTAINER_VIEW = 2; //容器view
    private static final int PROGRESS_VIEW = 3;  //progressbar

    private CircularProgreeBar mCircularProgreeBar;
    private View showingView;                    //当前被添加的view

    private LinearLayout containerLayout;        //view容器
    private ArrayList<View> views;               //存储所有刻意被添加的view
    private Context mContext;                    //上下文，

    private static ViewRegister single = null;

    public synchronized static ViewRegister getInstance() {
        if (single == null) {
            single = new ViewRegister();

        }
        return single;
    }

    /**
     * 初始化
     */

    public void register(Context mContext, LinearLayout caontainerLayout) {
        this.mContext = mContext;
        setContainerLayout(caontainerLayout);
        views = new ArrayList<View>();

    }

    public void setShowView(int view) {

        switch (view) {
            case LOGIN_VIEW:
                showingView = views.get(LOGIN_VIEW);
                break;
            case CONTAINER_VIEW:
                showingView = views.get(CONTAINER_VIEW);
                break;
            case PROGRESS_VIEW:
                showingView = views.get(PROGRESS_VIEW);
                CircularProgreeBar.getInstance()
                        .start();

                break;
        }

    }

    /**
     * 向容器中添加view
     */
    public void addShowView() {

        containerLayout.addView(showingView, 0);
    }

    /**
     * 移除容器中正在显示的view
     */
    public void removeShowView() {
        containerLayout.removeView(showingView);
        CircularProgreeBar.getInstance().stop();
    }

    public void removeAllView() {
        containerLayout.removeAllViews();
    }

    public void addView(View view) {
        this.views.add(view);

    }

    public View getView(int view) {
        return views.get(view);
    }


    /***/
    public LinearLayout getContainerLayout() {
        return containerLayout;
    }

    public void setContainerLayout(LinearLayout containerLayout) {
        this.containerLayout = containerLayout;
    }

    public View getShowingView() {
        return showingView;
    }

    public void setShowingView(View showingView) {
        this.showingView = showingView;
    }

    /**
     * 关闭单例
     */

    public void close() {

        if (single != null)
            single = null;

    }

}
