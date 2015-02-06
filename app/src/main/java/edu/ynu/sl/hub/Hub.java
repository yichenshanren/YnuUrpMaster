package edu.ynu.sl.hub;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import edu.ynu.sl.MainActivity;
import edu.ynu.sl.anim.AnimationRegister;
import edu.ynu.sl.status.*;
import edu.ynu.sl.ui.fragment.ImgFragment;
import edu.ynu.sl.ui.fragment.LibFragment;
import edu.ynu.sl.ui.fragment.ScheduleFragment;
import edu.ynu.sl.ui.view.*;

/**
 * Created by ku on 2014/12/27.
 */
public class Hub {
    private Context mContext;

    private static Hub single = null;

    public synchronized static Hub getInstance() {
        if (single == null) {
            single = new Hub();

        }
        return single;
    }

    public void init(Context message) {
        this.mContext = message;
    }


    private Handler handler = new Handler() {

        public void handleMessage(Message message) {

            switch (message.what) {

                case Msg.MESSAGE_LOGIN:  //点击登陆按钮，弹出加载界面

                    ViewRegister mRegister = ViewRegister.getInstance();
                    //	Util.showToast(mContext, "xiangxixi");

                    mRegister.removeShowView();     //移除登录界面

                    CircularProgreeBar.getInstance().setProgressText(
                            CircularProgreeBar.LOGIN_TEXT);

                    mRegister.setShowView(3);
                    mRegister.addShowView();       //添加加载界面

                    break;
                case Msg.MESSAGE_LOGIN_CANCLE: //点击登陆界面的取消按钮，移除登陆界面

                    ViewRegister mRegister0 = ViewRegister.getInstance();
                    mRegister0.removeShowView();     //移除登陆界面
                    AnimationRegister.getInstance()  //开始悬浮按钮的open动画
                            .getFloatingButtonAnimationCotroller()
                            .startOpenAnimation();

                    ((PublicAppView) ViewClassRegister.getInstance().getView(BaseView.VIEW_PUBLIC_APP)).setColor();
                    break;
                case Msg.MESSAGE_FLOATING_BUTTON: //点击悬浮按钮
                    AnimationRegister m = AnimationRegister.getInstance();

                    if (AppStatus.getInstance().getStatus(AppStatus.STATUS_FLOATING_OPEN)) {   //关闭悬浮按钮的子项，
                        ViewRegister.getInstance().setShowView(1);//添加登陆界面
                        ((LoginView) ViewClassRegister.getInstance().getView(BaseView.VIEW_LOGIN)).setColor();
                        m.getFloatingButtonAnimationCotroller()   //关闭动画
                                .startCloseAnimation();


                    } else {                    //打开悬浮按钮子项
                        ViewRegister.getInstance().removeShowView(); //移除登陆界面
                        m.getFloatingButtonAnimationCotroller()      //开始open动画
                                .startOpenAnimation();
                        ((PublicAppView) ViewClassRegister.getInstance().getView(BaseView.VIEW_PUBLIC_APP)).setColor();
                    }
                    break;
                case Msg.MESSAGE_FLOATING_CALDENER: //悬浮按钮的校历按钮
                    addFrag();
                    FragmentRegister.getInstance().addAfterFrame(new LibFragment());
                    ((TopAfterView) ViewClassRegister.getInstance().getView(BaseView.VIEW_TOP_AFTER)).addText(TopAfterView.CALDENER);
                    break;
                case Msg.MESSAGE_FLOATING_SCHEDULE: //悬浮按钮的本科课表纵览按钮
                    addFrag();
                    FragmentRegister.getInstance().addAfterFrame(new ScheduleFragment());
                    ((TopAfterView) ViewClassRegister.getInstance().getView(BaseView.VIEW_TOP_AFTER)).addText(TopAfterView.SCHEDULE);
                    break;
                case Msg.MESSAGE_FLOATING_TALENTEC: //悬浮按钮的人才培养按钮
                    addFrag();
                    FragmentRegister.getInstance().addAfterFrame(new ImgFragment());
                    ((TopAfterView) ViewClassRegister.getInstance().getView(BaseView.VIEW_TOP_AFTER)).addText(TopAfterView.TALENT);
                    break;


                case Msg.MESSAGE_HOMEBUTON: //点击homeButton

                    //   Util.showToast(mContext, "怎么回事");
                    AnimationRegister mAnimationRegister = AnimationRegister.getInstance();

			      /*判断homebutton的状态*/
                    if (AppStatus.getInstance().getStatus(AppStatus.STATUS_HOMEBUTTON_OPEN)) { //待点击状态

			    	  /* 让homebutton变成箭头 */
                        mAnimationRegister
                                .getHomeButtonAnimationCotroller()
                                .startHomeButtonBackAnimation();

                    } else { //已经点击过的状态

			    	  /*让homgbutton变成三*/
                        mAnimationRegister
                                .getHomeButtonAnimationCotroller()
                                .startHomeButtonAnimation();
                      /*切换为before视图*/
                        mAnimationRegister
                                .getTopAnimationCotroller()
                                .startTopInAnimation();
			    	  /*打开悬浮按钮*/
                        mAnimationRegister
                                .getFloatingButtonAnimationCotroller()
                                .startOpenAnimation();
                        ((PublicAppView) ViewClassRegister.getInstance().getView(BaseView.VIEW_PUBLIC_APP)).setColor();

			    	  /*移除当前被添加的视图*/
                        ViewRegister.getInstance().removeShowView();
                        //添加登录前的视图
                        FragmentRegister.getInstance().addFrameContent();
                        //移除选择器
                        ((SelectorContainer) ViewClassRegister.getInstance().getView(BaseView.VIEW_SELECTOR)).removeView();
			    	  /*设置before颜色*/
                        //  ((TopBeforeView) ViewClassRegister.getInstance().getView(BaseView.VIEW_TOP_BAFORE)).setColor();
                    }

                    break;
                case Msg.MESSAGE_BTN_BENKELOGIN: //点击本科生登陆按钮
                    AnimationRegister mm = AnimationRegister.getInstance();

                    if (AppStatus.getInstance().getStatus(AppStatus.STATUS_FLOATING_OPEN)) {   //关闭悬浮按钮的子项，
                        ViewRegister.getInstance().setShowView(1);//添加登陆界面
                        ((LoginView) ViewClassRegister.getInstance().getView(BaseView.VIEW_LOGIN)).setColor();
                        mm.getFloatingButtonAnimationCotroller()   //关闭动画
                                .startCloseAnimation();
                    }

                    break;
                case Msg.MESSAGE_LIST_TOUCH_DOWN://课表分类选择的列表事件
                    //当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview
                    ((MainActivity) ViewClassRegister.getInstance()
                            .getView(BaseView.VIEW_ACTIVITY))
                            .setScrollViewTouch(true);
                    break;
                case Msg.MESSAGE_LIST_TOUCH_CANCLE://课表分类选择的列表事件
                    ((MainActivity) ViewClassRegister.getInstance()
                            .getView(BaseView.VIEW_ACTIVITY))
                            .setScrollViewTouch(false);
                    break;

                case Msg.MESSAGE_SCHEDULE_CONTENT: //课表分类的单击事件更新选择的内容
                    ((TopAfterView) ViewClassRegister.getInstance()
                            .getView(BaseView.VIEW_TOP_AFTER))
                            .setContentText(
                                    Data.getInstance().getContent()
                            );

                    break;

                case Msg.MESSAGE_AFTERVIEW_SUBTITLE_BACK://顶部
                    ((TopAfterView) ViewClassRegister.getInstance()
                            .getView(BaseView.VIEW_TOP_AFTER)).addText(TopAfterView.TALENT_BACK);
                    break;
            }
        }
    };


    private void addFrag() {
        //	Util.showToast(mContext, "怎么回事");
		/*设置title颜色*/
        // ((TopAfterView)ViewClassRegister.getInstance().getView(BaseView.VIEW_TOP_AFTER)).setColor();
		/*添加选择器*/
        //  ((SelectorView) ViewClassRegister.getInstance().getView(BaseView.VIEW_SELECTOR)).addView();
		/*开始topOut动画*/
        AnimationRegister.getInstance().getTopAnimationCotroller().startTopOutAnimation();

		/* 添加显示fragment的界面 */
        ViewRegister.getInstance().setShowView(2);

		/* 开始悬浮按钮关闭动画 */
        AnimationRegister.getInstance().getFloatingButtonAnimationCotroller().startCloseAnimation();

		/*开始homeButton的打开动画*/
        AnimationRegister
                .getInstance()
                .getHomeButtonAnimationCotroller()
                .startHomeButtonBackAnimation();
        //设置content 为空
        ((TopAfterView) ViewClassRegister.getInstance()
                .getView(BaseView.VIEW_TOP_AFTER))
                .setContentText("");

    }

    private Handler getHandler() {
        return handler;
    }

    private void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 供各种事件调用，向本类handler发送消息
     */

    public void sendMessage(int messageWhat) {

        Message message = new Message();
        message.what = messageWhat;

        handler.sendMessage(message);
    }

}
