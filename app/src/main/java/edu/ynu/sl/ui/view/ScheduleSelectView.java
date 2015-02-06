package edu.ynu.sl.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.anim.Anim;
import edu.ynu.sl.ui.adapter.ScheduleClassifyAdapter;
import edu.ynu.sl.urp.iml.OnGetScheduleListener;
import edu.ynu.sl.urp.json.Urp;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.urp.struct.ScheduleContent;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/27.
 */
public class ScheduleSelectView extends BaseView {


    @InjectView(R.id.select_right)
    LinearLayout selectRightLayout;
    private LayoutInflater mInflater;
    private ViewPager viewPager;

    private ArrayList<ScheduleClassifyAdapter> rightAdapter;
    private ArrayList<View> mListViews;
    private ArrayList<ListView> listView;
    private Urp urp;

    private int selected;

    public ScheduleSelectView(Context mContext, View view) {
        super(mContext, view);
    }


    @Override
    public void initView(View view) {

        /*注入视图*/
        ButterKnife.inject(this, view);
        /*初始化又试图*/
        View sc = LayoutInflater.from(mContext).inflate(R.layout.schedule_selector, null);
        viewPager = (ViewPager) sc.findViewById(R.id.view_pager);
        initFragment();

        viewPager.setAdapter(new MyPagerAdapter());
        selectRightLayout.setLayoutTransition(new Anim().setupCustomAnimations());
        selectRightLayout.addView(sc, 0);

        /*获取初始值*/
        urp = new Urp(mContext);
        urp.setOnGetScheduleListener(new MyScheduleListener());
        urp.getSemesterList();
    }

    private void initFragment() {


    }

    @Override
    public void onClick(View v) {

    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public class leftItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            setSelected(position);
        }
    }

    public class rightItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        }
    }


    public class MyScheduleListener implements OnGetScheduleListener {

        @Override
        public void onGetScheduleEnd(ArrayList<ScheduleClass> scheduleClasses, int msg) {

            if (getSelected() < 5) {
                ScheduleClassifyAdapter s = new ScheduleClassifyAdapter(mContext, scheduleClasses);

            }

        }

        @Override
        public void onGetCourseEnd(ArrayList<ScheduleContent> course, int msg) {

        }
    }

    ArrayList<ScheduleClass> list = new ArrayList<ScheduleClass>();

    public ArrayList<ScheduleClass> getList() {
        list.add(new ScheduleClass("我"));
        list.add(new ScheduleClass("想"));
        list.add(new ScheduleClass("西"));
        list.add(new ScheduleClass("西"));
        list.add(new ScheduleClass("我"));
        list.add(new ScheduleClass("想"));
        list.add(new ScheduleClass("西"));
        list.add(new ScheduleClass("西"));
        return list;
    }

    private class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mListViews.size();
        }

        /**
         * 从指定的position创建page
         *
         * @param container ViewPager容器
         * @param position  The page position to be instantiated.
         * @return 返回指定position的page，这里不需要是一个view，也可以是其他的视图容器.
         */
        @Override
        public Object instantiateItem(View collection, int position) {


            ((ViewPager) collection).addView(mListViews.get(position), 0);

            return mListViews.get(position);
        }

        /**
         * <span style='font-family: "Droid Sans";'>从指定的position销毁page</span>
         * <p/>
         * <p/>
         * <span style='font-family: "Droid Sans";'>参数同上</span>
         */
        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView(mListViews.get(position));
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public void finishUpdate(View arg0) {
        }


        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

    }


}
