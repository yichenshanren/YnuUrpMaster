package edu.ynu.sl.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class RoateImg extends ImageView {

    private Context mContext;
    private int r = 0;

    public RoateImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = mContext;
    }

    public Animation rotate() {

        Animation mAnimation = new RotateAnimation(r, r += 45, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setDuration(500);
        mAnimation.setFillAfter(true);
        return mAnimation;
    }


    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == event.ACTION_DOWN) {

            this.startAnimation(rotate());


        }

        return true;
    }

}
