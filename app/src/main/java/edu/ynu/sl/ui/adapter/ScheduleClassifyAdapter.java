package edu.ynu.sl.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import edu.ynu.sl.R;
import edu.ynu.sl.urp.struct.ScheduleClass;
import edu.ynu.sl.util.Util;

import java.util.ArrayList;

/**
 * Created by ku on 2014/12/28.
 */
public class ScheduleClassifyAdapter extends BaseAdapter {

    private ArrayList<ScheduleClass> mClass;
    private Context mContext;
    private Boolean firstLeft = true;
    private int color;


    public ScheduleClassifyAdapter(Context mContext, ArrayList<ScheduleClass> mClass) {
        this.mContext = mContext;
        this.mClass = mClass == null ? new ArrayList<ScheduleClass>() : mClass;
        color = Util.getColor();
    }

    @Override
    public int getCount() {
        return mClass.size();
    }

    @Override
    public ScheduleClass getItem(int position) {
        return mClass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(ScheduleClass m) {
        mClass.add(m);
        notifyDataSetChanged();
    }

    public void clear() {

        mClass.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ScheduleClass> list) {

        for (int i = 0; i < list.size(); i++) {
            this.add(list.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ScheduleClass scheduleClass = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.schedule_classify_select_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setContent(scheduleClass.getValue());
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.textView1)
        TextView content;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setContent(String text) {
            content.setText(text);
            content.setBackgroundColor(color);

        }
    }
}
