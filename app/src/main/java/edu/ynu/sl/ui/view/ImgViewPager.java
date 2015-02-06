package edu.ynu.sl.ui.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import edu.ynu.sl.R;
import edu.ynu.sl.urp.struct.Image;
import edu.ynu.sl.urp.util.Util;
import edu.ynu.sl.urp.ynu.Img;

import java.util.ArrayList;

/**
 * Created by ku on 2015/1/6.
 */
public class ImgViewPager implements ViewPager.OnPageChangeListener {


    private ViewPager imgView;
    private Context mContext;
    private ArrayList<Image> imgList;
    private String cat;
    private int index;

    private ViewPagerAdapter adapter;
    private int currentItem;
    private View view;

    public ImgViewPager(Context mContext, ArrayList<Image> list, String cat, int index) {
        this.mContext = mContext;
        this.imgList = list;
        this.cat = cat;
        this.index = index;

    }

    private void initView() {
        initViewPager();
    }

    public void show() {
        Dialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.img_view);
        imgView = (ViewPager) window.findViewById(R.id.img_viewpager);
        initView();
    }

    private void initViewPager() {
        imgView.setOnPageChangeListener(this);
        ArrayList<ImageView> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new ImageView(mContext));
        }
        if (index > 0) {
            Picasso.with(mContext).load(Img.imgUrl + imgList.get(index - 1).getImaUrl()).into(list.get(0));
        }
        if (index < imgList.size()) {
            Picasso.with(mContext).load(Img.imgUrl + imgList.get(index + 1).getImaUrl()).into(list.get(2));
            Util.print(Img.imgDown + cat + "&" + imgList.get(index + 1).getDownloadUrl());
        }
        Picasso.with(mContext).load(Img.imgUrl + imgList.get(index).getImaUrl()).into(list.get(1));
        currentItem = index;
        adapter = new ViewPagerAdapter(list);
        imgView.setCurrentItem(1);
        imgView.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (adapter.getCount() > 1) { //多于1，才会循环跳转
            if (position == 1) { //首位之前，跳转到末尾（N）
                if (currentItem < imgList.size() - 1) {
                    if (!imgList.get(currentItem + 1).getDownloadUrl().equals("")) {
                        Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem++).getDownloadUrl()).into(adapter.getItem(2));
                    }
                } else {
                    currentItem = 0;
                    if (!imgList.get(currentItem).getDownloadUrl().equals("")) {
                        Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem).getDownloadUrl()).into(adapter.getItem(2));
                    }
                }
                imgView.setCurrentItem(1, false);
                if (currentItem > 0) {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem--).getDownloadUrl()).into(adapter.getItem(0));

                } else {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(imgList.size() - 1).getDownloadUrl()).into(adapter.getItem(0));
                }

                if (currentItem < imgList.size()) {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem++).getDownloadUrl()).into(adapter.getItem(2));
                } else {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(0).getDownloadUrl()).into(adapter.getItem(2));
                }
            } else if (position == 2) { //末位之后，跳转到首位（1）
                if (currentItem > 0) {
                    if (imgList.get(currentItem - 1).getDownloadUrl().equals("")) {
                        Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem--).getDownloadUrl()).into(adapter.getItem(2));
                    }
                } else {
                    currentItem = imgList.size() - 1;
                    if (imgList.get(currentItem).getDownloadUrl().equals("")) {
                        Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem).getDownloadUrl()).into(adapter.getItem(2));
                    }
                }
                if (currentItem > 0) {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem--).getDownloadUrl()).into(adapter.getItem(0));

                } else {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(imgList.size() - 1).getDownloadUrl()).into(adapter.getItem(0));
                }

                if (currentItem < imgList.size()) {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(currentItem++).getDownloadUrl()).into(adapter.getItem(2));
                } else {
                    Picasso.with(mContext).load(Img.imgDown + cat + "&" + imgList.get(0).getDownloadUrl()).into(adapter.getItem(2));
                }
                imgView.setCurrentItem(1, false); //false:不显示跳转过程的动画
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    public class ViewPagerAdapter extends PagerAdapter {
        private ArrayList<ImageView> list;

        public ViewPagerAdapter(ArrayList<ImageView> list) {
            this.list = list == null ? new ArrayList<ImageView>() : list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        public ImageView getItem(int position) {
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
}
