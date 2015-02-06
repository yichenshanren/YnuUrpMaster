package edu.ynu.sl.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.anim.Anim;
import edu.ynu.sl.anim.AnimationCotroller;
import edu.ynu.sl.anim.AnimationRegister;
import edu.ynu.sl.hub.Hub;
import edu.ynu.sl.hub.Msg;
import edu.ynu.sl.status.ViewClassRegister;
import edu.ynu.sl.ui.widget.RippleView;
import edu.ynu.sl.util.Util;

import java.util.Random;

/**
 * Created by ku on 2014/12/27.
 */
public class TopAfterView extends BaseView {

    public static final int STUDENT_IFO = 0;
    public static final int SCHEDULE = 1;
    public static final int CALDENER = 2;
    public static final int TALENT = 3;

    public static final int TALENT_BACK = 4;

    private Boolean xBoolean = false;
    private Anim mAnim;

    @InjectView(R.id.home_btn)
    RippleView mActionButton;
    @InjectView(R.id.home_img)
    ImageView mImageView;
    @InjectView(R.id.login_person_detail)
    RelativeLayout detaiLayout;
    @InjectView(R.id.title)
    RelativeLayout actionBar;
    @InjectView(R.id.detaile_0)
    TextView detail0;
    @InjectView(R.id.content_text)
    TextView content;


    public TopAfterView(Context mContext, View view) {
        super(mContext, view);
    }

    @Override
    public void initView(View view) {

        /*注册该view*/
        ViewClassRegister.getInstance().addView(this, BaseView.VIEW_TOP_AFTER);
        ButterKnife.inject(this, view);//注入视图

        mAnimationCotroller = new AnimationCotroller();
        mAnim = new Anim(mContext);

        mAnimationCotroller.registerHomeButtonAnimation(mImageView, mAnim);//在动画控制器中注册actionbutton
        AnimationRegister.getInstance().setHomeButtonAnimationCotroller(mAnimationCotroller); //把动画控制器注册

        //  setColor(); //设置初始颜色
        mActionButton.setOnClickListener(this); //设置默认的侦听事件


    }

    public void setColor() {
        Random r = new Random();
        actionBar.setBackgroundColor(Util.getColor(r));//设置actionbar的颜色
        detail0.setBackgroundColor(Util.getColor(r));  //设置标题的背景颜色
        //	detail0.setTextColor(Util.getColor(r));
    }

    public void addText(int type) {

        switch (type) {
            case STUDENT_IFO:

                break;
            case CALDENER:

                detail0.setText("图书馆");
                detail0.setTextSize(15);
                break;
            case SCHEDULE:
                detail0.setText("本科课表");
                detail0.setTextSize(15);
                break;

            case TALENT:

                detail0.setText("校园美图");
                detail0.setTextSize(15);
                break;
            case TALENT_BACK:
                detail0.setText("< 校园美图");
        }

    }


    public void setContentText(String text) {

        content.setText(text);
    }

    @Override
    public void onClick(View v) {
        Hub.getInstance().sendMessage(Msg.MESSAGE_HOMEBUTON);

    }
}
