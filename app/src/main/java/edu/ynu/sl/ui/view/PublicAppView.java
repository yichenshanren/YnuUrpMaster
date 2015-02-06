package edu.ynu.sl.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.anim.AnimationCotroller;
import edu.ynu.sl.anim.AnimationRegister;
import edu.ynu.sl.hub.Hub;
import edu.ynu.sl.hub.Msg;
import edu.ynu.sl.status.ViewClassRegister;
import edu.ynu.sl.status.ViewRegister;
import edu.ynu.sl.ui.widget.CircularImage;
import edu.ynu.sl.ui.widget.RippleView;
import edu.ynu.sl.util.Util;

import java.util.Random;

/**
 * Created by ku on 2014/12/27.
 */
public class PublicAppView extends BaseView {

    private ViewRegister mRegister;
    private AnimationCotroller mTopAnimationCotroller;
    private AnimationCotroller mOpenAnimationCotroller;

    private Boolean animEndBoolean = false;
    @InjectView(R.id.login_floating_button)
    RippleView xButton;//floating button
    @InjectView(R.id.login_floating_button_x)
    ImageView X;     //floating button x
    @InjectView(R.id.public_calender)
    RelativeLayout calenderButton; //school calender
    @InjectView(R.id.public_schedule)
    RelativeLayout scheduleButton; //Undergraduate schedule for all
    @InjectView(R.id.public_talent)
    RelativeLayout talentButton;     //人才培养方案
    @InjectView(R.id.login_imgbtn_calender)
    CircularImage calenderImageButton; //
    @InjectView(R.id.login_imgbtn_schedule)
    CircularImage scheduleImageButton;
    @InjectView(R.id.login_imgbtn_talent)
    CircularImage talentImageButton;
    @InjectView(R.id.text_ca)
    TextView caTextView;
    @InjectView(R.id.text_sc)
    TextView scTextView;
    @InjectView(R.id.text_ta)
    TextView taTextView;

    public PublicAppView(Context mContext, View view) {
        super(mContext, view);
    }


    @Override
    public void initView(View view) {
        /*注册该view*/
        ViewClassRegister.getInstance().addView(this, BaseView.VIEW_PUBLIC_APP);
        ButterKnife.inject(this, view); //注入视图
        /*设置公众应用按钮的图片资源*/
        calenderImageButton.setImageResource(R.drawable.ic_calendar_normal);
        scheduleImageButton.setImageResource(R.drawable.ic_schedule_all_normal);
        talentImageButton.setImageResource(R.drawable.ic_talent_normal);

        /*设置公众应用按钮的初始可见状态为不可见*/
        calenderButton.setVisibility(View.GONE);
        scheduleButton.setVisibility(View.GONE);
        talentButton.setVisibility(View.GONE);

       /*添加动画注册*/
        mOpenAnimationCotroller = new AnimationCotroller();
        mOpenAnimationCotroller.registerXAnimation(xButton, calenderButton, scheduleButton, talentButton);
        AnimationRegister.getInstance().setFloatingButtonAnimationCotroller(mOpenAnimationCotroller);
       /*开始初始动画*/
        mOpenAnimationCotroller.startOpenAnimation();
        animEndBoolean = true;


        /*设置事件*/
        xButton.setOnClickListener(this);
        calenderImageButton.setOnClickListener(this);
        scheduleImageButton.setOnClickListener(this);
        talentImageButton.setOnClickListener(this);
       /*设置初始颜色*/
        setColor();

    }

    public void setColor() {
        Random r = new Random();
        /*设置按钮的背景颜色*/
        calenderImageButton.setBackgroundColor(Util.getColor(r));
        scheduleImageButton.setBackgroundColor(Util.getColor(r));
        talentImageButton.setBackgroundColor(Util.getColor(r));
        xButton.setBackgroundColor(Util.getColor(r));
        /*设置按钮的文字颜色*/
        caTextView.setTextColor(Util.getColor(r));
        scTextView.setTextColor(Util.getColor(r));
        taTextView.setTextColor(Util.getColor(r));

    }

    @Override
    public void onClick(View v) {
        Hub mHub = Hub.getInstance();

        switch (v.getId()) {
            case R.id.login_imgbtn_calender:
                mHub.sendMessage(Msg.MESSAGE_FLOATING_CALDENER);
                break;
            case R.id.login_imgbtn_schedule:

                mHub.sendMessage(Msg.MESSAGE_FLOATING_SCHEDULE);
                break;
            case R.id.login_imgbtn_talent:

                mHub.sendMessage(Msg.MESSAGE_FLOATING_TALENTEC);
                break;
            case R.id.login_floating_button:

                mHub.sendMessage(Msg.MESSAGE_FLOATING_BUTTON);
                break;
        }


    }
}
