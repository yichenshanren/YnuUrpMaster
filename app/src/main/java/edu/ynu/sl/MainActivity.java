/**
 * Created by ku on 2014/12/27.
 */


package edu.ynu.sl;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.anim.AnimationCotroller;
import edu.ynu.sl.anim.AnimationRegister;
import edu.ynu.sl.hub.Hub;
import edu.ynu.sl.status.*;
import edu.ynu.sl.ui.view.BaseView;
import edu.ynu.sl.ui.view.SelectorContainer;
import edu.ynu.sl.ui.view.TopAfterView;
import edu.ynu.sl.ui.view.TopBeforeView;
import edu.ynu.sl.util.Util;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.before)
    LinearLayout beforeLinearLayout;      //page top animation before view
    @InjectView(R.id.after)
    LinearLayout afterLinearLayout;       //page top animation after view
    @InjectView(R.id.title)
    LinearLayout titleLinearLayout;       //page title
    @InjectView(R.id.container_line)
    LinearLayout containerFrameLayout;     //a container view
    @InjectView(R.id.scrollView1)
    ScrollView mScrollView;
    @InjectView(R.id.container_after)
    LinearLayout selector;
    private Boolean scrollTouch = true;


    private AnimationCotroller mAnimationCotroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*注入视图*/
        ButterKnife.inject(this);
        /*初始化控件*/
        initView();
        /*添加fragment*/
        addFragment();
        /*注册该类*/
        ViewClassRegister.getInstance().addView(this, BaseView.VIEW_ACTIVITY);

    }

    private void addFragment() {
        FragmentRegister.getInstance().init(this);
        Hub.getInstance().init(this);
        FragmentRegister.getInstance().addFrameContent();
    }

    private void initView() {
        // get view
        View beforeView = LayoutInflater.from(this).inflate(R.layout.top_anim_before, null);
        View afterView = LayoutInflater.from(this).inflate(R.layout.top_anim_after, null);
        ImageView title = new ImageView(this);

        //set view value
        title.setImageResource(R.drawable.login_logo);

        // add view onto container
        beforeLinearLayout.addView(beforeView);
        afterLinearLayout.addView(afterView);
        titleLinearLayout.addView(title);

        afterView.setVisibility(View.GONE);

        //instantiation

        new TopBeforeView(this, beforeView);
        new TopAfterView(this, afterView);
        new SelectorContainer(selector);
        //   new SelectorView(this, selector);

        //Register topAnimation
        addTopAnimation(beforeView, afterView, title, containerFrameLayout);
    }

    /**
     * add animation to animation cotroller
     */
    private void addTopAnimation(View beforeView, View afterView, View title, LinearLayout con) {
        mAnimationCotroller = new AnimationCotroller();
        mAnimationCotroller.registerTopAnimation(beforeView, afterView, title, con);

        AnimationRegister.getInstance().setTopAnimationCotroller(mAnimationCotroller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Util.showToast(this, "missing xixi");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setScrollViewTouch(Boolean touch) {
        if (scrollTouch != touch) {
            mScrollView.requestDisallowInterceptTouchEvent(touch);
            scrollTouch = touch;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppStatus.getInstance().close();
        CircularProgreeBar.getInstance().close();
        FragmentRegister.getInstance().close();
        ViewClassRegister.getInstance().close();
        ViewRegister.getInstance().close();
    }

}
