package com.example.dong.comic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.comic.model.Chap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DONG on 07-Mar-17.
 */

public class AsyncTacskListChap extends AsyncTask<String, Void, Void> {
    Context context;
    ArrayList<Chap> listChap=new ArrayList<>();
    ProgressDialog progressDialog;

    public AsyncTacskListChap(Context context) {
        this.context = context;
        progressDialog=new ProgressDialog(context);
    }

    public ArrayList<Chap> getListchap() {
        return listChap;
    }

    public void setListchap(ArrayList<Chap> listchap) {
        this.listChap = listchap;
    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Waiting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }



    @Override
    protected Void doInBackground(String... strings) {

        Document document = null;
        try {
            document = (Document) Jsoup.connect(strings[0]).get();
            if (document != null) {
                Elements listnew=document.select("div.box.chap-list");
                Elements listnew1=listnew.select("ul");

                Elements subjectElements = listnew1.select("li");

                if (subjectElements != null && subjectElements.size() > 0) {
                    for (Element element : subjectElements) {
                        Element titleSubject = element.getElementsByTag("a").first();
                        if (titleSubject != null) {
                            Chap chap=new Chap();
                            String title = titleSubject.attr("title");
                            String href = titleSubject.attr("href");
                            Log.e("----", "doInBackground: " + titleSubject);
                            chap.setTitle(title);
                            chap.setHref(href);
                            listChap.add(chap);
                        }
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();

    }
}