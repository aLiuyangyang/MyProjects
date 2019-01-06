package com.example.dell.myprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dell.myprojects.bean.BannerBean;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    private List<BannerBean.ResultBean> list;


    public MyPagerAdapter(Context context, List<BannerBean.ResultBean> mData) {
        this.context = context;
        this.list = mData;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(context).load(list.get(position%list.size()).getImageUrl()).into(imageView);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
