package com.example.dong.comic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.dong.comic.adapter.MyPagerAdapter;
import com.example.dong.comic.model.Image;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by DONG on 07-Mar-17.
 */

public class ImageActivity extends AppCompatActivity {
    ArrayList<Image> list= new ArrayList<>();
    PagerAdapter myPagerAdapter;
    ViewPager viewPager;
    PagerSlidingTabStrip tabs;
    Toolbar toolbar;
    EditText editPage;
    Button btnPage;
    int currentPage;
    String href;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);

        addControl();
        addEvent();


    }

    private void addEvent() {
        Intent intent=getIntent();
        href=intent.getStringExtra("href");

        AsyncTaskListImage asyncTaskListImage= new AsyncTaskListImage(this);
        asyncTaskListImage.execute(href);
        try {
            list=asyncTaskListImage.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        myPagerAdapter=new MyPagerAdapter(this,list);
        viewPager.setAdapter(myPagerAdapter);


        btnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page=Integer.parseInt(editPage.getText().toString());
                if(page>list.size())
                {
                    Toast.makeText(ImageActivity.this, "Không có trang này!!!", Toast.LENGTH_SHORT).show();
                }
                else
                    viewPager.setCurrentItem(page-1);
            }
        });

        tabs.setViewPager(viewPager);

        PageListener pageListener = new PageListener();
        viewPager.setOnPageChangeListener(pageListener);

//        //tabs.getChildAt(currentPage).setSelected(true);
//        viewPager.setCurrentItem(currentPage);


    }

    private void addControl() {
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.pager);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//mũi tên quay về
       // actionBar.setIcon();

        editPage= (EditText) findViewById(R.id.edit_page);

        btnPage= (Button) findViewById(R.id.btnPage);
    }


    //lưu trạng thái
    public void savingPreferences(){
        SharedPreferences pre=this.getSharedPreferences("my_data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        editor.putInt("pageCurent",currentPage);
        editor.putString("chapCurent",href);
        editor.commit();
    }

    //đọc trạng thái
    public void restoringPreferences(){
        SharedPreferences pre=this.getSharedPreferences("my_data", MODE_PRIVATE);
        int currentPage=pre.getInt("pageCurent",3);
        String chapCurent=pre.getString("chapCurent","");
        if(href.equals(chapCurent))
            viewPager.setCurrentItem(currentPage);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //gọi hàm lưu trạng thái
        savingPreferences();
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //gọi hàm đọc trạng thái
        restoringPreferences();
        Toast.makeText(this,"Đọc tiếp trang "+(currentPage+1), Toast.LENGTH_SHORT).show();
    }

    private class PageListener extends ViewPager.SimpleOnPageChangeListener{
        public void onPageSelected(int position) {
            currentPage = position;
            Log.i("---", "page selected " +  position);

        }
    }
}

