package edu.ynu.sl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.ynu.sl.ui.fragment.ContentFragment;

import java.util.ArrayList;

/**
 * ViewPager实现画廊效果
 *
 * @author Trinea 2013-04-03
 */
public class test extends FragmentActivity {

    private static int TOTAL_COUNT = 3;

    private RelativeLayout viewPagerContainer;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_selector);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerContainer = (RelativeLayout) findViewById(R.id.pager_layout);

        fragments = new ArrayList<Fragment>();

        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentPagerAdapter);
        // to cache all page, or we will see the right item delayed
        viewPager.setOffscreenPageLimit(TOTAL_COUNT);
        viewPager.setPageMargin(0);
        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
        viewPager.setOnPageChangeListener(myOnPageChangeListener);


//        fragments.add(new ScheduleSelectorFragment());
//        fragments.add(new ScheduleSelectorFragment());
//        fragments.add(new ScheduleSelectorFragment());
        fragments.add(new ContentFragment());
        fragments.add(new ContentFragment());
        fragments.add(new ContentFragment());
        fragments.add(new ContentFragment());
        tabFragmentPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1);
        viewPagerContainer.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
                return viewPager.dispatchTouchEvent(event);
            }
        });

    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return TOTAL_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(test.this);
            imageView.setImageResource(R.drawable.login_bg + position);
            TextView textView = new TextView(test.this);
            textView.setText("我");
            textView.setTextColor(Color.RED);
            View view = LayoutInflater.from(test.this).inflate(R.layout.login, null);

            ((ViewPager) container).addView(textView, position);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        public TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg) {

            return fragments.get(arg);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}