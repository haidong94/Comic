package com.example.dong.comic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dong.comic.model.Chap;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        restoringPreferences();
        addEvent();


    }


    private void addEvent() {



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String href=listChap.get(position).getHref();
                Intent intent=new Intent(MainActivity.this,ImageActivity.class);
                intent.putExtra("href",href);
                startActivity(intent);

            }
        });
    }

    private void addControl() {
        listview= (ListView) findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<Chap>(MainActivity.this, android.R.layout.simple_list_item_1 ,listChap);

        listview.setAdapter(arrayAdapter);
    }


    public void restoringPreferences(){
        SharedPreferences pre=this.getSharedPreferences("my_data", MODE_PRIVATE);
        final String chapCurent=pre.getString("chapCurent","");
        if(chapCurent!=null)
        {
            AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
            b.setTitle("Question");
            b.setMessage("Bạn có muốn đọc tiếp không???");
            b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent intent=new Intent(MainActivity.this,ImageActivity.class);
                    intent.putExtra("href",chapCurent);
                    startActivity(intent);
                }});
            b.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which)

                {

                    dialog.cancel();

                }

            });
            b.create().show();

        }

    }

}

