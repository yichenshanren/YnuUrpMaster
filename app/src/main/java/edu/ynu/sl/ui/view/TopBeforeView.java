package edu.ynu.sl.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.hub.Hub;
import edu.ynu.sl.hub.Msg;
import edu.ynu.sl.status.ViewClassRegister;
import edu.ynu.sl.ui.widget.RippleView;
import edu.ynu.sl.util.Util;

import java.util.Random;

/**
 * Created by ku on 2014/12/27.
 */
public class TopBeforeView extends BaseView {

    @InjectView(R.id.relativeLayout11)
    RelativeLayout bgLayout;
    @InjectView(R.id.before_bg)
    RelativeLayout hengtiaoLayout;

    @InjectView(R.id.login_benke)
    RippleView btnRippleView;
    @InjectView(R.id.btn_benke)
    TextView textView;

    public TopBeforeView(Context mContext, View view) {
        super(mContext, view);
    }

    @Override
    public void initView(View view) {

        /*
        * 注册该view*/
        ViewClassRegister.getInstance().addView(this, BaseView.VIEW_TOP_BAFORE);
        ButterKnife.inject(this, view);  //诸如试图

        //   setColor();               //set initial color
        btnRippleView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Hub.getInstance().sendMessage(Msg.MESSAGE_BTN_BENKELOGIN);

    }

    public void setColor() {
        Random random = new Random();

        if (Util.draw()) {
            bgLayout.setBackgroundResource(R.drawable.login_bg);
        } else {
            bgLayout.setBackgroundColor(Util.getColor(random));
        }
        hengtiaoLayout.setBackgroundColor(Util.getColor(random));

        textView.setBackgroundColor(Util.getColor(random));
        //	textView.setTextColor(Util.getColor(random));
    }
}
