package edu.ynu.sl.ui.view;

import android.animation.LayoutTransition;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.ui.fragment.SelectorFragment;
import edu.ynu.sl.ui.iml.OnSelectorItemClickListener;
import edu.ynu.sl.urp.struct.ScheduleClass;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/30.
 */
public class SelectorView {

    private ActionBarActivity mContext; //
    private View v;

    private ArrayList<SelectorFragment> fragments;
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;
    private LayoutTransition layoutTransition;
    private OnSelectorItemClickListener onSelectorItemClickListener;
    private int correctItem;//当前页卡id
    private int count;// 页卡总数

    @InjectView(R.id.pager_layout)
    RelativeLayout layout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    public SelectorView(ActionBarActivity mContext) {
        this.mContext = mContext;
        initView();

    }

    private void initView() {
        //获取所需布局
        v = LayoutInflater.from(mContext).inflate(R.layout.schedule_selector, null);
        //注入视图
        ButterKnife.inject(this, v);
        //初始fragment
        initFragment();


        //初始化viewPager
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(mContext.getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentPagerAdapter);
        // to cache all page, or we will see the right item delayed
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(5);
        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);


        //viewPager.setCurrentItem(1);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });

    }

    public View getView() {
        return v;
    }

    public void init(int count) {
        this.count = count;
    }

    private void initFragment() {
        fragments = new ArrayList<SelectorFragment>();
        setCorrectItem(-1);
    }

    /**
     * 添加item
     * <p/>
     * *
     */
    public void addItem() {
        //根据当前的页卡号判断是否增加新的页卡
        if (correctItem + 1 < tabFragmentPagerAdapter.getCount()) {
            //切换到下一个页卡
            tabFragmentPagerAdapter.getItem(correctItem + 1).removeList();
            tabFragmentPagerAdapter.getItem(correctItem + 1).addProgressBar();
            correctItem++;
            viewPager.setCurrentItem(correctItem);
            //下一个页卡以后的所有页卡都为空
            for (int i = correctItem; i < fragments.size(); i++) {
                tabFragmentPagerAdapter.getItem(i).removeList();
            }
        } else {
            correctItem++; // 页卡号加1
            int index = correctItem;
            if (count == 4) { // 没多大关系
                index++;
            }
            //添加一个全新的页卡
            SelectorFragment fragment = new SelectorFragment();
            fragment.init(index, onSelectorItemClickListener, new ArrayList<ScheduleClass>());

            tabFragmentPagerAdapter.add(fragment);
            viewPager.setCurrentItem(correctItem);
        }
    }

    /**
     * 更新item
     */
    public void updateItem(ArrayList<ScheduleClass> list) {

        tabFragmentPagerAdapter.getItem(correctItem).update(list);
        tabFragmentPagerAdapter.getItem(correctItem).removeProgressBar();
        tabFragmentPagerAdapter.getItem(correctItem).addList();

    }

    public int getCorrectItem() {
        return correctItem;
    }

    public void setCorrectItem(int correctItem) {
        this.correctItem = correctItem;
    }

    public void setOnSelectorItemClickListener(OnSelectorItemClickListener onSelectorItemClickListener) {
        this.onSelectorItemClickListener = onSelectorItemClickListener;
    }

    public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<SelectorFragment> fragments;

        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<SelectorFragment>();

        }

        @Override
        public SelectorFragment getItem(int arg) {

            return fragments.get(arg);
        }

        public void add(SelectorFragment fragment) {
            this.fragments.add(fragment);
            notifyDataSetChanged();
        }

        public void remove(int position) {
            this.fragments.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            correctItem = position;
            for (int i = 0; i < tabFragmentPagerAdapter.getCount(); i++) {
                if (i != correctItem) {
                    tabFragmentPagerAdapter.getItem(i).setEnable(false);
                } else {
                    tabFragmentPagerAdapter.getItem(i).setEnable(true);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (layout != null) {
                layout.invalidate();
            }
            //    correctItem = position;


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


}
