package edu.ynu.sl.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import edu.ynu.sl.R;
import edu.ynu.sl.status.AppStatus;
import edu.ynu.sl.status.ViewRegister;
import edu.ynu.sl.ui.widget.RippleView;

/**
 * Created by ku on 2014/12/27.
 */
public class AnimationCotroller {
    private View beforeView;
    private View afterView;
    private View titleView;

    private RelativeLayout bgLayout;

    private LinearLayout container;
    private ImageView homeButton;

    private AnimatorSet beforeAnimatorSet;
    private AnimatorSet afterAnimatorSet;
    private AnimatorSet titleAnimatorSet;
    private AnimatorSet bgAnimatorSet;
    private AnimatorSet containerAnimatorSet;
    private MyAnimationDrawable myAnimationDrawable;

    private AppStatus appStatus = AppStatus.getInstance();

    private AnimatorSet openAnimatorSet;

    private RippleView xButton;

    private RelativeLayout calenderButton;
    private RelativeLayout scheduleButton;

    private RelativeLayout talentButton;


    /**
     * 注册页面顶部动画
     */
    public void registerTopAnimation(View beforeView, View afterView, View title, LinearLayout container) {
        this.beforeView = beforeView;
        this.titleView = title;
        this.afterView = afterView;
        this.container = container;
        this.bgLayout = (RelativeLayout) beforeView.findViewById(R.id.before_bg);
    }

    /**
     * 注册悬浮按钮动画
     */

    public void registerXAnimation(RippleView xButton, RelativeLayout calenderButton, RelativeLayout scheduleButton, RelativeLayout talentButton) {
        this.xButton = xButton;
        this.calenderButton = calenderButton;
        this.scheduleButton = scheduleButton;
        this.talentButton = talentButton;


    }

    /**
     * 开始页面顶部向上切换动画
     */
    @SuppressLint("NewApi")
    public void startTopOutAnimation() {
        Anim mAnim = new Anim();

        beforeAnimatorSet = mAnim.beforeOut(beforeView);
        afterAnimatorSet = mAnim.afterIn(afterView);
        titleAnimatorSet = mAnim.titleUp(titleView);
        bgAnimatorSet = mAnim.alpha(bgLayout, 1);
        containerAnimatorSet = mAnim.translet(container, 0, 0);


        beforeAnimatorSet.start();
        afterAnimatorSet.start();
        titleAnimatorSet.start();
        bgAnimatorSet.start();
        containerAnimatorSet.start();

        beforeAnimatorSet.addListener(new OnAnimatorListener(0));
        afterAnimatorSet.addListener(new OnAnimatorListener(1));
        titleAnimatorSet.addListener(new OnAnimatorListener(2));

    }

    /**
     * 开始顶部视图向下切换动画
     */
    @SuppressLint("NewApi")
    public void startTopInAnimation() {
        Anim mAnim = new Anim();

        beforeAnimatorSet = mAnim.beforeIn(beforeView);
        afterAnimatorSet = mAnim.afterOut(afterView);
        titleAnimatorSet = mAnim.titleDown(titleView);
        bgAnimatorSet = mAnim.alpha(bgLayout, 0);
        containerAnimatorSet = mAnim.translet(container, 1, 0);

        beforeAnimatorSet.start();
        afterAnimatorSet.start();
        titleAnimatorSet.start();
        bgAnimatorSet.start();
        containerAnimatorSet.start();

        beforeAnimatorSet.addListener(new OnAnimatorListener(3));
        afterAnimatorSet.addListener(new OnAnimatorListener(4));
        titleAnimatorSet.addListener(new OnAnimatorListener(5));

    }


    public void startOpenAnimation() {
        appStatus.addStatus(AppStatus.STATUS_FLOATING_OPEN, true);
        openAnimatorSet = new Anim().translet(xButton, 1, 1);
        openAnimatorSet.setDuration(500);
        openAnimatorSet.start();
        openAnimatorSet.addListener(new OnAnimatorListener(7));


    }

    /**
     * 开始悬浮按钮关闭子项动画
     */
    public void startCloseAnimation() {
        appStatus.addStatus(AppStatus.STATUS_FLOATING_OPEN, false);
        Anim mAnim = new Anim();
        openAnimatorSet = mAnim.doAnimateClose(calenderButton, 0, 3, 200);
        openAnimatorSet.start();
        openAnimatorSet = mAnim.doAnimateClose(scheduleButton, 1, 3, 200);
        openAnimatorSet.start();
        openAnimatorSet = mAnim.doAnimateClose(talentButton, 2, 3, 200);
        openAnimatorSet.start();

        openAnimatorSet.addListener(new OnAnimatorListener(6));

    }

    /**
     * 注册homebutton的旋转切换动画
     */
    private Anim anim;

    public void registerHomeButtonAnimation(ImageView mImageView, Anim mAnim) {
        homeButton = mImageView;
        anim = mAnim;
    }


    /**
     * 开始homebuton的动画             三  转换为 <---
     */
    public void startHomeButtonBackAnimation() {
        myAnimationDrawable = new MyAnimationDrawable(anim.xToE()) {

            @Override
            void onAnimationEnd() {
                appStatus.addStatus(AppStatus.STATUS_HOMEBUTTON_OPEN, false);

            }
        };
        homeButton.setBackgroundDrawable(myAnimationDrawable);
        myAnimationDrawable.start();

    }

    /**
     * 开始homebuton的动画             <--- 转换为 三
     */
    public void startHomeButtonAnimation() {
        myAnimationDrawable = new MyAnimationDrawable(anim.eToX()) {

            @Override
            void onAnimationEnd() {

                appStatus.addStatus(AppStatus.STATUS_HOMEBUTTON_OPEN, true);
            }
        };
        homeButton.setBackgroundDrawable(myAnimationDrawable);
        myAnimationDrawable.start();


    }

    /**
     * animator的侦听器
     */
    @SuppressLint("NewApi")
    class OnAnimatorListener implements Animator.AnimatorListener {

        int anim;

        public OnAnimatorListener(int anim) {
            this.anim = anim;
        }


        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            // TODO Auto-generated method stub
            if (anim == 0) {
                AnimationRegister.getInstance().setTopAnimEnd(true);
            }
            if (anim == 3) {
                AnimationRegister.getInstance().setTopAnimEnd(false);
            }
            if (anim == 6) {

                calenderButton.setVisibility(View.GONE);
                scheduleButton.setVisibility(View.GONE);
                talentButton.setVisibility(View.GONE);

                openAnimatorSet = new Anim().translet(xButton, 0, 1);
                openAnimatorSet.setDuration(500);
                openAnimatorSet.start();
                openAnimatorSet.addListener(new OnAnimatorListener(8));
            }
            if (anim == 7) {

                Anim mAnim = new Anim();

                openAnimatorSet = mAnim.doAnimateOpen(calenderButton, 0, 3, 200);
                openAnimatorSet.start();
                openAnimatorSet = mAnim.doAnimateOpen(scheduleButton, 1, 3, 200);
                openAnimatorSet.start();
                openAnimatorSet = mAnim.doAnimateOpen(talentButton, 2, 3, 200);
                openAnimatorSet.start();

            }

            if (anim == 8) {
                ViewRegister.getInstance().addShowView();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }
    }


    /**
     * 处理AnimationDrawable动画的侦听动画结束
     */
    public abstract class MyAnimationDrawable extends AnimationDrawable {
        Handler finishHandler;      // 判断结束的Handler

        public MyAnimationDrawable(AnimationDrawable ad) {
            // 这里得自己把每一帧加进去
            for (int i = 0; i < ad.getNumberOfFrames(); i++) {
                this.addFrame(ad.getFrame(i), ad.getDuration(i));
            }
        }

        @Override
        public void start() {
            super.start();
            /**
             * 首先用父类的start()
             * 然后启动线程，来调用onAnimationEnd()
             */
            finishHandler = new Handler();
            finishHandler.postDelayed(
                    new Runnable() {
                        public void run() {
                            onAnimationEnd();
                        }
                    }, getTotalDuration());
        }

        /**
         * 这个方法获得动画的持续时间（之后调用onAnimationEnd()）
         */
        public int getTotalDuration() {
            int durationTime = 0;
            for (int i = 0; i < this.getNumberOfFrames(); i++) {
                durationTime += this.getDuration(i);
            }
            return durationTime;
        }

        /**
         * 结束时调用的方法，一定要实现
         */
        abstract void onAnimationEnd();
    }

}
