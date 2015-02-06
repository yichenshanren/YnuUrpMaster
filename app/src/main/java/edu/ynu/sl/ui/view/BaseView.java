package edu.ynu.sl.ui.view;
/**
 * Created by ku on 2014/12/27.
 */

import edu.ynu.sl.anim.AnimationCotroller;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseView implements OnClickListener {

    public final static String VIEW_LOGIN = "loginview";
    public final static String VIEW_PUBLIC_APP = "publicview";
    public final static String VIEW_TOP_AFTER = "afterview";
    public final static String VIEW_TOP_BAFORE = "beforeview";
    public final static String VIEW_ACTIVITY = "main";
    public final static String VIEW_SELECTOR = "selectorview";


    protected View view;
    protected Context mContext;
    protected AnimationCotroller mAnimationCotroller;


    public BaseView(Context mContext, View view) {

        this.view = view;
        this.mContext = mContext;
        initView(view);

    }

    public abstract void initView(View view);
}
