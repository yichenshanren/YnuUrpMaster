package edu.ynu.sl.urp.iml;

import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.urp.struct.ScheduleContent;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/27.
 */
public interface OnGetScheduleListener {
    public void onGetScheduleEnd(ArrayList<ScheduleClass> scheduleClasses, int msg);

    public void onGetCourseEnd(ArrayList<ScheduleContent> course, int msg);
}
