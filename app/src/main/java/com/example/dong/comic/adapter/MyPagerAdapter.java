package com.example.dong.comic.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.dong.comic.R;
import com.example.dong.comic.model.Image;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by DONG on 09-Mar-17.
 */

public class MyPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Image> list=new ArrayList<>();


    public MyPagerAdapter(Context context, ArrayList<Image> list) {
        mContext = context.getApplicationContext();
        this.list=list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View View = mLayoutInflater.inflate(R.layout.custom_image_layout, container, false);

        ImageView imageView = (ImageView) View.findViewById(R.id.image);
        Glide.with(mContext)
                .load(list.get(position).getOrg())
                .into(imageView);
        PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(imageView);
        photoViewAttacher.update();
        container.addView(View);

        return View;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return (position+1)+" of "+list.size();
    }
}