package edu.ynu.sl.status;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import edu.ynu.sl.R;
import edu.ynu.sl.ui.fragment.ContentFragment;

/**
 * Created by ku on 2014/12/27.
 */
public class FragmentRegister {

    private ContentFragment mFragment;
    private ActionBarActivity mContext;

    private static FragmentRegister single = null;

    public static synchronized FragmentRegister getInstance() {
        if (single == null) {
            single = new FragmentRegister();

        }
        return single;
    }

    public void init(ActionBarActivity mContext) {

        this.mContext = mContext;
        mFragment = new ContentFragment();
    }

    public void addFrameContent() {

        FragmentManager fm = mContext.getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.firstcontainer, mFragment);
        ft.commit();

    }

    public void removeFragContent() {
        FragmentManager fm = mContext.getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.firstcontainer, new Fragment());
        ft.commit();
    }

    public void addAfterFrame(Fragment fragment) {

        FragmentManager fm = mContext.getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.firstcontainer, fragment);
        ft.commit();

    }

    public void close() {
        if (single != null) {
            single = null;
        }
    }

}
