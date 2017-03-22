package com.example.dong.comic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dong.comic.adapter.MyPagerAdapter;
import com.example.dong.comic.model.Image;
import com.example.dong.comic.interFace.ICallback;
import com.example.dong.comic.interFace.IListImage;

import java.util.ArrayList;

/**
 * Created by DONG on 07-Mar-17.
 */

public class ImageActivity extends AppCompatActivity implements IListImage,ICallback {
    ArrayList<Image> list= new ArrayList<>();
    PagerAdapter myPagerAdapter;
    ViewPager viewPager;
    LinearLayout toplayout;
    ImageButton btnPage;
    int currentPage;
    String href;
    boolean touch=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);
        addControl();
        addEvent();
    }

    public void addEvent() {
        Intent intent=getIntent();
        href=intent.getStringExtra("href");

        AsyncTaskListImage asyncTaskListImage= new AsyncTaskListImage(this);
        asyncTaskListImage.execute(href);
        list=asyncTaskListImage.getList();

        myPagerAdapter=new MyPagerAdapter(this,list);

        viewPager.setAdapter(myPagerAdapter);
        btnPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ImageActivity.this);
                alertDialog.setTitle("Pager");
                alertDialog.setMessage("Enter Pager");

                final EditText input = new EditText(ImageActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_page);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int page = Integer.parseInt(input.getText().toString());
                                if (page>list.size()) {
                                    Toast.makeText(ImageActivity.this, "Không có trang này!!!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    viewPager.setCurrentItem(page-1);
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

        //lưu trạng thái
        PageListener pageListener = new PageListener();
        viewPager.setOnPageChangeListener(pageListener);
    }
    private void addControl() {
        viewPager= (ViewPager) findViewById(R.id.pager);
        toplayout= (LinearLayout) findViewById(R.id.toplayout);
        btnPage= (ImageButton) findViewById(R.id.btnPage);
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
        int currentPage=pre.getInt("pageCurent",4);
        String chapCurent=pre.getString("chapCurent","");
        if(href.equals(chapCurent)){
            viewPager.setCurrentItem(currentPage);
        }
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

    }

    @Override
    public void processFinish() {
        myPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void callBack() {
        if(touch){
            toplayout.setVisibility(View.VISIBLE);
            touch=false;
            Toast a=Toast.makeText(ImageActivity.this, (currentPage+1)+"/"+list.size(), Toast.LENGTH_SHORT);
            a.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,100);
            a.show();

            toplayout.postDelayed(new Runnable() {
                public void run() {
                    toplayout.setVisibility(View.INVISIBLE);
                    touch = true;
                }
            }, 2500);
        }else {
            toplayout.setVisibility(View.INVISIBLE);
            touch=true;
        }
    }


    private class PageListener extends ViewPager.SimpleOnPageChangeListener{
        public void onPageSelected(int position) {
            currentPage = position;
            Toast a=Toast.makeText(ImageActivity.this, (currentPage+1)+"/"+list.size(), Toast.LENGTH_SHORT);
            a.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,100);
            a.show();
        }
    }

}

