package edu.ynu.sl.ui.view;

import android.animation.LayoutTransition;
import android.view.View;
import android.widget.LinearLayout;
import edu.ynu.sl.anim.Anim;
import edu.ynu.sl.status.ViewClassRegister;
import edu.ynu.sl.util.Util;

/**
 * Created by ku on 2015/1/1.
 */
public class SelectorContainer {

    private LinearLayout container;
    private Boolean viewIsAdd = false;
    private LayoutTransition layoutTransition;
    private View view;

    public SelectorContainer(LinearLayout container) {
        this.container = container;
        layoutTransition = new Anim().selectorAnim();
        container.setLayoutTransition(layoutTransition);
        ViewClassRegister.getInstance().addView(this, BaseView.VIEW_SELECTOR);
    }


    public void addSelector(View view) {
        if (!viewIsAdd) {
            Util.print("add");
            container.addView(view, 0);
            this.view = view;
            viewIsAdd = true;
        }
    }

    public void removeView() {
        if (viewIsAdd) {

            container.removeView(view);
            view = null;
            viewIsAdd = false;
        }
    }
}
