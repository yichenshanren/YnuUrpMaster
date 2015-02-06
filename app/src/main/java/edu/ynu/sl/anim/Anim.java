/**
 * Created by ku on 2014/12/27.
 */

package edu.ynu.sl.anim;

import android.animation.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import edu.ynu.sl.R;

public class Anim {

    private int beforeOut = -100;   //距离
    private int topTime = 1000;     //动画时长蜂

    private Context mContext;

    public Anim() {

    }

    public Anim(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NewApi")
    public AnimatorSet beforeOut(View view) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        beforeOut = -view.getHeight() * 2 / 5 - 10;
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, 0),
                ObjectAnimator.ofFloat(view, "translationY", 0, -view.getHeight() * 2 / 5 - 10));

//                ObjectAnimator.ofFloat(view, "alpha", 1, 1f));
        //动画周期为500ms
        set.setDuration(topTime);


        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet beforeIn(View view) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, 0),
                ObjectAnimator.ofFloat(view, "translationY", -view.getHeight() / 3, 0));

//                ObjectAnimator.ofFloat(view, "alpha", 0.7f, 1));
        //动画周期为500ms
        set.setDuration(topTime);
        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet afterOut(View view) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
//                ObjectAnimator.ofFloat(view, "translationX", 0, 0),
//                ObjectAnimator.ofFloat(view, "translationY", 0, -view.getHeight()),
//
                ObjectAnimator.ofFloat(view, "alpha", 1, 0f));
        //动画周期为500ms
        set.setDuration(topTime);
        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet afterIn(View view) {
        view.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
//                ObjectAnimator.ofFloat(view, "translationX", 0, 0),
//                ObjectAnimator.ofFloat(view, "translationY", -120, 0),

                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f));
        //动画周期为500ms
        set.setDuration(topTime);
        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet titleUp(View view) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, -view.getWidth() / 17),
                ObjectAnimator.ofFloat(view, "translationY", 0, -view.getHeight() / 2 - view.getHeight() / 4),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.7f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.7f));
        //动画周期为500ms
        set.setDuration(1000);
        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet titleDown(View view) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", -view.getWidth() / 15, 0),
                ObjectAnimator.ofFloat(view, "translationY", -view.getHeight() / 2 - view.getHeight() / 4, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 0.6f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0.6f, 1f));
        //动画周期为500ms
        set.setDuration(1000);
        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet alpha(View view, int s) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        if (s == 0) {
            set.playTogether(

                    ObjectAnimator.ofFloat(view, "alpha", 0f, 1f));


        } else {
            set.playTogether(

                    ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        }
        //动画周期为500ms
        set.setDuration(1000);
        return set;
    }

    @SuppressLint("NewApi")
    public AnimatorSet translet(View view, int s, int type) {
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        int translat = -250;
        if (type == 0) {
            translat = beforeOut;
        }

        if (s == 0) {
            set.playTogether(

                    ObjectAnimator.ofFloat(view, "translationX", 0, 0),
                    ObjectAnimator.ofFloat(view, "translationY", 0, translat));


        } else {
            set.playTogether(

                    ObjectAnimator.ofFloat(view, "translationX", 0, 0),
                    ObjectAnimator.ofFloat(view, "translationY", translat, 0));

        }
        //动画周期为500ms
        set.setDuration(1000);
        return set;
    }


    public AnimationDrawable xToE() {


        AnimationDrawable mAnimationDrawable = new AnimationDrawable();
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_00), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_01), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_02), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_03), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_04), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_05), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_06), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_07), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_08), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_09), 10);

        mAnimationDrawable.setOneShot(true);
        return mAnimationDrawable;

    }

    public AnimationDrawable eToX() {
        AnimationDrawable mAnimationDrawable = new AnimationDrawable();
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_10), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_11), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_12), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_13), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_14), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_15), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_16), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_17), 10);
        mAnimationDrawable.addFrame(mContext.getResources().getDrawable(R.drawable.x_00), 10);

        mAnimationDrawable.setOneShot(true);
        return mAnimationDrawable;

    }


    public AnimatorSet doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total));
        int translationX = (int) (radius * Math.cos(degree));
        int translationY = (int) (radius * Math.sin(degree));


        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1 * 500);

        return set;
    }

    public AnimatorSet doAnimateClose(final View view, int index, int total,
                                      int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total));
        int translationX = (int) (radius * Math.cos(degree));
        int translationY = (int) (radius * Math.sin(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));


        set.setDuration(1 * 500);


        return set;
    }

    // 生成自定义动画
    public LayoutTransition setupCustomAnimations() {
        // 动画：CHANGE_APPEARING
        // Changing while Adding
        LayoutTransition mTransitioner = new LayoutTransition();

//        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
//        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
//        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0,
//                1);
//        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom",
//                0, 1);
//        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",
//                1f, 0f, 1f);
//        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY",
//                1f, 0f, 1f);
//
//        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
//                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX,
//                pvhScaleY).setDuration(
//                mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
//        changeIn.addListener(new AnimatorListenerAdapter() {
//            public void onAnimationEnd(Animator anim) {
//                View view = (View) ((ObjectAnimator) anim).getTarget();
//                view.setScaleX(1f);
//                view.setScaleY(1f);
//            }
//        });
//
//        // 动画：CHANGE_DISAPPEARING
//        // Changing while Removingw
//        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
//        Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
//        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
//        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
//                "rotation", kf0, kf1, kf2);
//        final ObjectAnimator changeOut = ObjectAnimator
//                .ofPropertyValuesHolder(this, pvhLeft, pvhTop, pvhRight,
//                        pvhBottom, pvhRotation)
//                .setDuration(
//                        mTransitioner
//                                .getDuration(LayoutTransition.CHANGE_DISAPPEARING));
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
//                changeOut);
//        changeOut.addListener(new AnimatorListenerAdapter() {
//            public void onAnimationEnd(Animator anim) {
//                View view = (View) ((ObjectAnimator) anim).getTarget();
//                view.setRotation(0f);
//            }
//        });

        // 动画：APPEARING
        // Adding

        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationY", -800f, 1f).setDuration(
                mTransitioner.getDuration(LayoutTransition.APPEARING));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();

            }
        });

        // 动画：DISAPPEARING
        // Removing
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translationX", 1f, 800f).setDuration(
                mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setTranslationX(1f);
            }
        });
        return mTransitioner;

    }

    public LayoutTransition selectorAnim() {
        // 动画：CHANGE_APPEARING
        // Changing while Adding
        LayoutTransition mTransitioner = new LayoutTransition();


        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationX", -800f, 1f).setDuration(
                mTransitioner.getDuration(LayoutTransition.APPEARING));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();

            }
        });

        // 动画：DISAPPEARING
        // Removing
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translationX", 1f, 800f).setDuration(
                mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setTranslationX(1f);
            }
        });
        return mTransitioner;

    }
}
