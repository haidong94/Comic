package com.example.dong.comic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dong.comic.model.Chap;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String URL = "http://1.truyentranhmoi.com/o-long-vien-linh-vat-song/";
    ListView listview;
    ArrayList<Chap> listChap=new ArrayList<>();
    ArrayAdapter<Chap> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            AsyncTacskListChap asyncTacskListChap= new AsyncTacskListChap(this);
            asyncTacskListChap.execute(URL);
            listChap=asyncTacskListChap.getListchap();


        addControl();
        addEvent();


    }


    private void addEvent() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String hrel=listChap.get(position).getHref();
                Intent intent=new Intent(MainActivity.this,ImageActivity.class);
                intent.putExtra("href",hrel);
                startActivity(intent);

            }
        });
    }

    private void addControl() {
        listview= (ListView) findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<Chap>(MainActivity.this, android.R.layout.simple_list_item_1 ,listChap);

        listview.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AsyncTacskListChap asyncTacskListChap= new AsyncTacskListChap(this);
//        asyncTacskListChap.execute(URL);
//        listChap=asyncTacskListChap.getListchap();
    }
}
