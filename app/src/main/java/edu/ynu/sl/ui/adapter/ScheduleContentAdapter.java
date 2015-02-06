package edu.ynu.sl.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.urp.struct.ScheduleContent;
import edu.ynu.sl.urp.util.Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ku on 2015/1/3.
 */
public class ScheduleContentAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ScheduleContent> list;

    private int statusOverColor;
    private int statusColor;
    private int creditColor;
    private int propertyColor;
    private int bgColor;
    private int bgOverColor;

    public ScheduleContentAdapter(Context mContext, ArrayList<ScheduleContent> list) {
        this.mContext = mContext;
        this.list = list == null ? new ArrayList<ScheduleContent>() : list;
        initColor();
    }

    private void initColor() {
        Random r = new Random();
        statusColor = mContext.getResources().getColor(R.color.urp_green_depth);
        statusOverColor = mContext.getResources().getColor(R.color.urp_red);
        creditColor = Util.getColor(r);
        propertyColor = Util.getColor(r);
        bgColor = mContext.getResources().getColor(R.color.over_red);
        bgOverColor = mContext.getResources().getColor(R.color.over_green);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ScheduleContent getItem(int position) {
        return list.get(position);
    }

    public void add(ScheduleContent content) {
        this.list.add(content);
        notifyDataSetInvalidated();
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ScheduleContent> newList) {
        if (newList != null) {
            for (int i = 0; i < newList.size(); i++) {
                add(newList.get(i));
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ScheduleContent content = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.schedule_content, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setValue(content);

        return convertView;
    }


    public class ViewHolder {
        //视图注入
        @InjectView(R.id.course_name)
        TextView name;
        //        @InjectView(R.id.course_code) TextView code;
        @InjectView(R.id.classify_text)
        TextView classify;
        @InjectView(R.id.course_grade)
        TextView credit;
        @InjectView(R.id.course_time)
        TextView time;
        @InjectView(R.id.course_room)
        TextView room;
        @InjectView(R.id.course_teacher)
        TextView teacher;
        //        @InjectView(R.id.course_exam) TextView examTime;
        @InjectView(R.id.selected_status)
        TextView status;
        //        @InjectView(R.id.course_remark) TextView remark;
        @InjectView(R.id.course_bg)
        RelativeLayout bg;

        public ViewHolder(View view) {
            //注入视图
            ButterKnife.inject(this, view);
        }

        public void setValue(ScheduleContent content) {
            //课程名称
            name.setText(content.getCourseName());
            if (content.getCourseName().length() > 17) {
                name.setTextSize(14);
            }
            //教学班代码
//            code.setText("码 | " + content.getCourseCode());
//            code.setBackgroundColor(Util.getColor());
            //课程性质
            classify.setText(Util.getClassify(content.getCourseProperty()));
            classify.setBackgroundColor(propertyColor);
            //课程学分
            credit.setText(content.getCourseCredit());
            credit.setBackgroundColor(creditColor);
            //课程状态
            status.setText(Util.getCourseStatus(content.getCourseSelected(), content.getCourseCount())
                    + " | "
                    + content.getCourseSelected() + "/" + content.getCourseCount());
            if (Util.getCourseStatus(content.getCourseSelected(), content.getCourseCount()).equals("满")) {
                status.setBackgroundColor(statusOverColor);
                //设置背景颜色
                //  name.setBackgroundColor(bgColor);
            } else {
                status.setBackgroundColor(statusColor);
                //设置背景颜色
                //  name.setBackgroundColor(bgOverColor);
            }
            //教师
            teacher.setText("师 | " + content.getCourseTeacher());
            teacher.setBackgroundColor(Util.getColor());
            //教室
            room.setText(Util.getCourseRoom(content.getCourseRoom()));
            room.setBackgroundColor(Util.getColor());
//            //考试时间
//            examTime.setText("考 | " + content.getCourseExamTime());
//            examTime.setBackgroundColor(Util.getColor());
            //上课时间
            if (content.getCourseName().contains("形势")) {
                time.setText("周五 567");
            } else {
                time.setText(Util.getCourseTime(content.getCourseTime()));
            }
            time.setBackgroundColor(Util.getColor());
            //备注信息
//            remark.setText("备注：" + content.getCourseRemark());
//            remark.setBackgroundColor(Util.getColor());


        }

    }
}
