package edu.ynu.sl.urp.iml;

import edu.ynu.sl.urp.struct.Image;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/4.
 */
public interface OnGetImgLisener {
    public void onGetImgFinish(ArrayList<Image> imgUrl);
}
