package com.example.dong.comic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dong.comic.adapter.MyPagerAdapter;
import com.example.dong.comic.model.Image;

import java.util.ArrayList;

/**
 * Created by DONG on 07-Mar-17.
 */

public class ImageActivity extends AppCompatActivity{
   // RecyclerView recyclerView;
    ArrayList<Image> list=null;
//    RecyclerAdapter arrayAdapter;
//    LinearLayoutManager linearLayoutManager;
    MyPagerAdapter myPagerAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);
        Intent intent=getIntent();
        String href=intent.getStringExtra("href");

        AsyncTaskListImage asyncTaskListImage= new AsyncTaskListImage(this);
        asyncTaskListImage.execute(href);
        list=asyncTaskListImage.getList();

//        recyclerView= (RecyclerView) findViewById(R.id.recycerview);
//        recyclerView.setHasFixedSize(true);
//        // Setting the LayoutManager.
//        linearLayoutManager=new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        arrayAdapter = new RecyclerAdapter(list,ImageActivity.this);
//        recyclerView.setAdapter(arrayAdapter);

        myPagerAdapter=new MyPagerAdapter(this,list);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(myPagerAdapter);


    }
}
