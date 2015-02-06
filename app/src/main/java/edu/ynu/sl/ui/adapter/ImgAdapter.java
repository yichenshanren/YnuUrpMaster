package edu.ynu.sl.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
import edu.ynu.sl.R;
import edu.ynu.sl.urp.struct.Image;

import edu.ynu.sl.urp.ynu.Img;
import edu.ynu.sl.util.Util;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/4.
 */
public class ImgAdapter extends BaseAdapter {
    private ArrayList<Image> imgList;
    private Context mContext;

    public ImgAdapter(Context mContext, ArrayList<Image> list) {
        this.mContext = mContext;
        this.imgList = list == null ? new ArrayList<Image>() : list;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Image getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Image item) {
        this.imgList.add(item);
        notifyDataSetInvalidated();
    }

    public void addAll(ArrayList<Image> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        }
    }

    public void clear() {
        this.imgList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Image item = getItem(position);
        ImgHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.img_item, null);
            holder = new ImgHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ImgHolder) convertView.getTag();
        }

        holder.setValue(item);
        return convertView;
    }

    public class ImgHolder {
        @InjectView(R.id.img_text)
        TextView title;
        @InjectView(R.id.imageView)
        ImageView img;

        public ImgHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void setValue(Image img) {
            if (!img.getTitle().equals("")) {
                this.title.setText(img.getTitle().replaceAll("\r|\n", " | "));
                if (img.getTitle().length() > 2) {
                    this.title.setTextSize(10);
                }
                this.title.setBackgroundColor(Util.getColor());
            } else {
                this.title.setHeight(0);
            }
            if (!img.getImaUrl().equals("")) {
                Picasso.with(mContext).load(Img.imgUrl + img.getImaUrl()).into(this.img);
            }
            Util.print("最美护士" + img.getImaUrl());
        }
    }
}
