package edu.ynu.sl.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ynu.sl.R;
import edu.ynu.sl.ui.widget.HoloCircularProgressBar;
import edu.ynu.sl.util.Util;

import java.util.Random;

/**
 * Created by ku on 2015/1/2.
 */
public class ProgressBarView {

    public static final int LOGIN_TEXT = 0;
    public static final int LOAD_TEXT = 1;

    private Context mContext;
    private HoloCircularProgressBar mBar;
    private View mView;
    private ObjectAnimator mProgressBarAnimator;
    private boolean mAnimationHasEnded = false;
    private Boolean animRunBoolean = false;
    private LinearLayout containerLayout;
    private TextView progressText;

    private String logining;
    private String loading;

    public ProgressBarView(Context mContext) {

        this.mContext = mContext;
        this.mView = LayoutInflater.from(mContext).inflate(R.layout.progress_bar, null);

        this.mBar = (HoloCircularProgressBar)
                mView.findViewById(R.id.holoCircularProgressBar1);

        this.progressText = (TextView)
                mView.findViewById(R.id.progress_text);
        logining = mContext.getResources().getString(R.string.logining);
        loading = mContext.getResources().getString(R.string.loading);
    }


    public void addToView(LinearLayout view) {
        this.containerLayout = view;
        view.addView(mBar, 0);
        start();
    }

    public void removeFromView() {
        containerLayout.removeView(mBar);
        stop();
    }

    public void start() {
        setColor();
        if (!animRunBoolean) {
            animRunBoolean = true;
            animate(mBar, new Animator.AnimatorListener() {

                @Override
                public void onAnimationCancel(final Animator animation) {
                    animation.end();
                }

                @Override
                public void onAnimationEnd(final Animator animation) {
                    if (!mAnimationHasEnded) {
                        animate(mBar, this);
                    } else {
                        mAnimationHasEnded = false;
                    }
                }

                @Override
                public void onAnimationRepeat(final Animator animation) {
                }

                @Override
                public void onAnimationStart(final Animator animation) {
                }

            });


        }
    }


    public View getView() {
        return mView;
    }

    public void stop() {
        if (animRunBoolean) {
            animRunBoolean = false;
            mAnimationHasEnded = true;
            mProgressBarAnimator.cancel();

        }
    }

    private void setColor() {
        Random random = new Random();
        mBar.setProgressColor(Util.getColor(random));
        mBar.setProgressBackgroundColor(Util.getColor(random));
        progressText.setTextColor(Util.getColor(random));
    }

    public void setProgressText(int text) {
        switch (text) {
            case LOGIN_TEXT:
                progressText.setText(logining);
                break;
            case LOAD_TEXT:

                progressText.setText(loading);
                break;


        }

    }

    private void animate(final HoloCircularProgressBar progressBar,
                         final Animator.AnimatorListener listener) {
        final float progress = (float) (Math.random() * 2);
        int duration = 2000;
        animate(progressBar, listener, progress, duration);
    }

    private void animate(final HoloCircularProgressBar progressBar, final Animator.AnimatorListener listener,
                         final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }
}


