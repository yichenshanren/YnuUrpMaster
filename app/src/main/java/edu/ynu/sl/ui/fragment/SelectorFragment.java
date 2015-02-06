package edu.ynu.sl.ui.fragment;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.anim.Anim;
import edu.ynu.sl.status.CircularProgreeBar;
import edu.ynu.sl.ui.adapter.ScheduleClassifyAdapter;
import edu.ynu.sl.ui.iml.OnSelectorItemClickListener;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.util.Util;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/1.
 */
public class SelectorFragment extends Fragment implements AdapterView.OnItemClickListener {
    @InjectView(R.id.selector_container)
    LinearLayout container;
    @InjectView(R.id.selector_title)
    TextView title;

    private Handler handler;
    private View progressBar;
    private ListView list;
    private int type;

    private LayoutTransition layoutTransition;
    private OnSelectorItemClickListener onSelectorItemClickListener;
    private ScheduleClassifyAdapter adaper;

    private Boolean progressBarIsAdd = false;
    private Boolean listIsAdd = false;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.schedule_selector_item, container, false);
        Util.print("怎么回事");
        initView(view);

        return view;
    }

    private void initView(View view) {
        ButterKnife.inject(this, view);
        layoutTransition = new Anim().selectorAnim();
        container.setLayoutTransition(layoutTransition);
        initProgressBar();
        setList();
        setTitle();
        addProgressBar();

    }

    public void addList() {
        if (!listIsAdd) {
            container.addView(list, 0);
            listIsAdd = true;
        }
    }

    public void removeList() {
        if (listIsAdd) {
            container.removeView(list);
            listIsAdd = false;
        }
    }

    public void init(int type, OnSelectorItemClickListener o, ArrayList<ScheduleClass> list) {

        this.type = type;
        this.onSelectorItemClickListener = o;
        this.listContent = list;
    }

    public void update(ArrayList<ScheduleClass> listContent) {
        adaper.clear();

        if (listContent == null) {
            listContent = new ArrayList<ScheduleClass>();
        }
        adaper.addAll(listContent);
        Util.print(listContent.size() + "size");
        for (int i = 0; i < listContent.size(); i++) {
            Util.print(listContent.get(i).getValue());
        }
        Util.print(adaper.getCount() + "尺寸");
    }

    private void setTitle() {
        switch (type) {
            case 0:
                title.setText("选择学期");
                break;
            case 1:
                title.setText("选择学院");
                break;
            case 2:
                title.setText("选择专业");
                break;
            case 3:
                title.setText("选择年级");
                break;
            case 4:
                title.setText("选择课程性质");
                break;
        }
        title.setBackgroundColor(Util.getColor());

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

    public void initProgressBar() {

        //ProgressBarView progressBarView = new ProgressBarView(getActivity());
        progressBar = CircularProgreeBar.getInstance().getView();
        /*设置progress的文字*/
        CircularProgreeBar.getInstance().setProgressText(CircularProgreeBar.NOTHING_TEXT);
        /*启动progressbar*/
        CircularProgreeBar.getInstance().start();
    }

    public void setList() {
        ListView list = new ListView(getActivity());

        //   getListContent();
        adaper = new ScheduleClassifyAdapter(getActivity(), null);
        adaper.addAll(listContent);
        list.setAdapter(adaper);
        list.setOnItemClickListener(this);
        this.list = list;
    }

    ArrayList<ScheduleClass> listContent = new ArrayList<ScheduleClass>();

    public ArrayList<ScheduleClass> getListContent() {
        listContent.add(new ScheduleClass("我"));
        listContent.add(new ScheduleClass("想"));
        listContent.add(new ScheduleClass("西"));
        listContent.add(new ScheduleClass("西"));
        listContent.add(new ScheduleClass("我"));
        listContent.add(new ScheduleClass("想"));
        listContent.add(new ScheduleClass("西"));
        listContent.add(new ScheduleClass("西"));
        return listContent;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onSelectorItemClickListener.onSelectorItemClick(position, adaper.getItem(position), type);
    }

    public OnSelectorItemClickListener getOnSelectorItemClickListener() {
        return onSelectorItemClickListener;
    }

    public void setOnSelectorItemClickListener(OnSelectorItemClickListener onSelectorItemClickListener) {
        this.onSelectorItemClickListener = onSelectorItemClickListener;
    }

    public void setEnable(boolean b) {
        list.setEnabled(b);
    }
}
