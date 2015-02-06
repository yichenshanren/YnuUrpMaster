package edu.ynu.sl.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import edu.ynu.sl.R;
import edu.ynu.sl.hub.Hub;
import edu.ynu.sl.hub.Msg;
import edu.ynu.sl.status.CircularProgreeBar;
import edu.ynu.sl.status.Data;
import edu.ynu.sl.status.ViewClassRegister;
import edu.ynu.sl.ui.adapter.ScheduleContentAdapter;
import edu.ynu.sl.ui.iml.OnSelectorItemClickListener;
import edu.ynu.sl.ui.view.BaseView;
import edu.ynu.sl.ui.view.SelectorContainer;
import edu.ynu.sl.ui.view.SelectorView;
import edu.ynu.sl.ui.widget.NoScrollListView;
import edu.ynu.sl.urp.iml.OnGetScheduleListener;
import edu.ynu.sl.urp.json.Urp;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.urp.struct.ScheduleContent;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/27.
 */
public class ScheduleFragment extends Fragment implements OnGetScheduleListener {
    public static final int SCHOOL = 0;//学院
    public static final int MAJOR = 1;//专业
    public static final int TEACHGRADE = 2;//年级
    public static final int COURSEPORPERTRY = 3;//课程性质
    public static final int CLASSCOURSE = 4;
    // public static final int
    protected View progressView;//进度条
    protected TextView topView; //没什么用
    protected View contentView; //内容列表
    protected LinearLayout container;
    protected SelectorView selectorView;//选择器
    private SelectorContainer selectorContainer;//添加选择器的容器布局
    private NoScrollListView courseList;//课程列表
    private ScheduleContentAdapter courseAdapter; //课程列表的适配器
    protected Urp urp;

    private String[] keys = {
            "",
            "",
            "",
            "",
            ""
    };
    private Data content;
    private Boolean progressBarIsAdd = false;
    /*topview*/
    private ListView select;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, container, false);
        //获取容器布局
        this.container = (LinearLayout) view.findViewById(R.id.frame_container);
        //获取porgeessbar
        progressView = CircularProgreeBar.getInstance().getView();

        content = Data.getInstance();
        // 添加顶部空白布局
        topView = new TextView(getActivity());
        topView.setHeight(250);
        //   addView(topView, 0);

        //实例urp获取数据
        urp = new Urp(getActivity());
        urp.setOnGetScheduleListener(this);
        urp.getSemesterList();

        //添加选择器布局
        selectorContainer = (SelectorContainer) ViewClassRegister.getInstance().getView(BaseView.VIEW_SELECTOR);
        selectorView = new SelectorView(
                (ActionBarActivity) ViewClassRegister.getInstance().getView(BaseView.VIEW_ACTIVITY)
        );
        selectorView.init(5);
        selectorView.setOnSelectorItemClickListener(new MyListern());
        selectorView.addItem();
        selectorContainer.addSelector(selectorView.getView());
        //初始课表列表
        courseList = new NoScrollListView(getActivity());
        courseAdapter = new ScheduleContentAdapter(getActivity(), null);
        courseList.setAdapter(courseAdapter);

        return view;

    }

    public void addProgressView() {
        if (!progressBarIsAdd) {
            addView(topView, 0);
            addView(progressView, 1);
        /*设置progress的文字*/
            CircularProgreeBar.getInstance().setProgressText(CircularProgreeBar.LOAD_TEXT);
		/*启动progressbar*/
            CircularProgreeBar.getInstance().start();
            progressBarIsAdd = true;

        }
    }

    private void addCourseList(ArrayList<ScheduleContent> course) {
        courseList.setBackgroundColor(Color.rgb(231, 231, 231));
        topView.setHeight(50);
        addView(topView, 0);
        addView(courseList, 1);
        courseAdapter.addAll(course);
    }

    public void addView(View view, int index) {

        container.addView(view, index);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        container.removeAllViews();
        selectorView = null;
        //  selectorView.removeView();
    }

    public void removeProgressBar() {
        if (progressBarIsAdd) {
            container.removeAllViews();
            progressBarIsAdd = false;
        }
    }

    @Override
    public void onGetScheduleEnd(ArrayList<ScheduleClass> scheduleClasses, int msg) {
        if (selectorView != null) {
            selectorView.updateItem(scheduleClasses);
        }
    }

    @Override
    public void onGetCourseEnd(ArrayList<ScheduleContent> course, int msg) {
        removeProgressBar();
        addCourseList(course);

    }

    private class MyListern implements OnSelectorItemClickListener {
        @Override
        public void onSelectorItemClick(int postion, ScheduleClass data, int type) {
            switch (type) {
                case SCHOOL:
                    urp.getSchoolList(doSomething(SCHOOL, data));
                    break;
                case MAJOR:
                    urp.getMajorList(doSomething(MAJOR, data));
                    break;
                case TEACHGRADE:
                    urp.getTeachGradeList(doSomething(TEACHGRADE, data));
                    break;
                case COURSEPORPERTRY:
                    urp.getCoursePropertry(doSomething(COURSEPORPERTRY, data));
                    break;
                case CLASSCOURSE:
                    urp.getClassCourse(doSomething(CLASSCOURSE, data));
                    selectorContainer.removeView();
                    addProgressView();
                    break;
            }

        }
    }

    private String doSomething(int type, ScheduleClass data) {
        keys[type] = "/" + data.getKey();
        content.updateContent(data.getValue(), type);
        if (type != CLASSCOURSE)
            selectorView.addItem();
        Hub.getInstance().sendMessage(Msg.MESSAGE_SCHEDULE_CONTENT);

        String key = "";
        for (int i = 0; i <= type; i++) {
            key += (type > MAJOR) ? (i != MAJOR ? keys[i] : "") : keys[i];

        }

        return key;
    }
}
