package edu.ynu.sl.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
public class LoginView extends BaseView {

    @InjectView(R.id.user_name)
    EditText userNameEditText;  //用户名
    @InjectView(R.id.user_password)
    EditText passEditText;      //密码
    @InjectView(R.id.user_authcode)
    EditText authEditText;    //验证码
    @InjectView(R.id.user_authcode_img)
    ImageView codeImageView;  //验证码图片
    @InjectView(R.id.login_bg)
    RelativeLayout bgLayout;
    @InjectView(R.id.btn_login)
    TextView loginBtnText;
    @InjectView(R.id.btn_cancel)
    TextView loginCancleText;


    @InjectView(R.id.login_btn)
    RippleView loginBtn;       //登陆按钮
    @InjectView(R.id.login_cancel)
    RippleView loginCancleBtn;  //登陆取消按钮

    public LoginView(Context mContext, View view) {
        super(mContext, view);
    }

    @Override
    public void initView(View view) {

        /*注册该view*/
        ViewClassRegister.getInstance().addView(this, BaseView.VIEW_LOGIN);
        /*注入视图*/
        ButterKnife.inject(this, view);
        /*绑定事件*/
        loginBtn.setOnClickListener(this);
        loginCancleBtn.setOnClickListener(this);

		/*设置初始颜色*/

        setColor();
    }

    public void setColor() {
        Random r = new Random();

        bgLayout.setBackgroundColor(Util.getColor(r));
        loginBtnText.setBackgroundColor(Util.getColor(r));
//	loginBtnText.setTextColor(Util.getColor(r));
//	loginCancleText.setTextColor(Util.getColor(r));
        loginCancleText.setBackgroundColor(Util.getColor(r));
//	userNameEditText.setBackgroundColor(Util.getColor(r));
//	passEditText.setBackgroundColor(Util.getColor(r));
//	authEditText.setBackgroundColor(Util.getColor(r));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                Hub.getInstance().sendMessage(Msg.MESSAGE_LOGIN);
                break;
            case R.id.login_cancel:
                Hub.getInstance().sendMessage(Msg.MESSAGE_LOGIN_CANCLE);
                break;

            default:
                break;
        }

    }
}
