package com.example.dong.comic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.dong.comic.model.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by DONG on 11-Apr-17.
 */

public class AsyncTaskComic extends AsyncTask<String,Void,Comic>{
    Context context;
    Comic comic=new Comic();
    ProgressDialog progressDialog;

    public AsyncTaskComic(Context context){
        this.context=context;
        progressDialog=new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Waiting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }
    @Override
    protected Comic doInBackground(String... params) {
        Document document = null;
        try {
            document = (Document) Jsoup.connect(params[0]).get();
            if (document != null) {
                Elements listnew=document.select("truyen-info.entry-header");
               // Elements listnew1=listnew.select("li");

                Elements subjectElements = listnew.select("li");

//                if (subjectElements != null && subjectElements.size() > 0) {
//                    for (Element element : subjectElements) {
//                        Element titleSubject = element.getElementsByTag("a").first();
//                        if (titleSubject != null) {
//                            Chap chap=new Chap();
//                            String title = titleSubject.attr("title");
//                            String href = titleSubject.attr("href");
//                            Log.e("----", "doInBackground: " + titleSubject);
//                            chap.setTitle(title);
//                            chap.setHref(href);
//                            listChap.add(chap);
//                        }
//                    }
//                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return comic;
    }
}
