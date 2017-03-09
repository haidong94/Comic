package com.example.dong.comic.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dong.comic.R;
import com.example.dong.comic.model.Image;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by DONG on 07-Mar-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<Image> list;
    private Context context;

    public RecyclerAdapter(ArrayList<Image> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //  public Integer getItem(int position){
    //      return list.get(position);
    // }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            imageView= (ImageView) itemView.findViewById(R.id.image);

           // itemView.setOnClickListener(this);
        }


   //     @Override
//        public void onClick(View v) {
//            int id=list.get(getPosition()).intValue();
//            Intent intent=new Intent(v.getContext(), TestActivity.class);
//            intent.putExtra("exam",id);
//            v.getContext().startActivity(intent);
//
//
//
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.custom_image_layout,parent,false);
        return new RecyclerAdapter.RecyclerViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerAdapter.RecyclerViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getOrg())
                .into(holder.imageView);
        PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(holder.imageView);
        photoViewAttacher.update();

    }


}
