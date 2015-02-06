package edu.ynu.sl.ui.fragment;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import edu.ynu.sl.R;
import edu.ynu.sl.anim.Anim;
import edu.ynu.sl.status.CircularProgreeBar;
import edu.ynu.sl.status.ViewRegister;
import edu.ynu.sl.ui.view.LoginView;
import edu.ynu.sl.ui.view.PublicAppView;

/**
 * Created by ku on 2014/12/27.
 */
public class ContentFragment extends Fragment {
    private ViewRegister mRegister;
    private LayoutTransition mLayoutTransition;

    private LinearLayout containerLayout;
    private View publicView;
    private View loginView;
    private View container;
    private View progressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_layout, container, false);

        mLayoutTransition = new Anim().setupCustomAnimations();
        intiVew(view);
        return view;

    }

    private void intiVew(View view) {
        //容器布局
        containerLayout = (LinearLayout) view.findViewById(R.id.frame_container);

        //设置transition
        containerLayout.setLayoutTransition(mLayoutTransition);

        //获取view布局
        publicView = LayoutInflater.from(getActivity()).inflate(R.layout.urp_public_app, null);
        loginView = LayoutInflater.from(getActivity()).inflate(R.layout.login, null);
        container = LayoutInflater.from(getActivity()).inflate(R.layout.container_layout, null);
        progressView = LayoutInflater.from(getActivity()).inflate(R.layout.progress_bar, null);

        //注册progressBar
        CircularProgreeBar.getInstance().init(getActivity(), progressView);


        //注册view
        mRegister = ViewRegister.getInstance();
        mRegister.register(getActivity(), containerLayout);
        mRegister.addView(publicView);
        mRegister.addView(loginView);
        mRegister.addView(container);
        mRegister.addView(progressView);


        containerLayout.addView(publicView);
        new PublicAppView(getActivity(), publicView);
        new LoginView(getActivity(), loginView);

    }

}
