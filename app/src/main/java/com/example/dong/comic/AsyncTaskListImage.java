package com.example.dong.comic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dong.comic.model.Image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DONG on 08-Mar-17.
 */

public class AsyncTaskListImage extends AsyncTask<String,Void,ArrayList<Image>> {
    ArrayList<Image> list=new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;

    public AsyncTaskListImage() {
        super();
    }

    public AsyncTaskListImage(Context context) {
        this.context=context;
        progressDialog=new ProgressDialog(context);
    }

    public ArrayList<Image> getList() {
        return list;
    }

    public void setList(ArrayList<Image> list) {
        this.list = list;
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
        progressDialog.setMessage("waiting...");
        progressDialog.show();
    }

    @Override
    protected ArrayList<Image> doInBackground(String... params) {
        Document document = null;
        try {
            document = (Document) Jsoup.connect(params[0]).get();
            if (document != null) {
                Elements listnew=document.select("div.image-chap");



                Elements subjectElements = listnew.select("img");

                    if (subjectElements != null && subjectElements.size() > 0) {
                        for (Element element : subjectElements) {
                            Image image = new Image();
                            String org = element.attr("src");
                            Log.e("----", "doInBackground: " + org);
                            image.setOrg(org);
                            list.add(image);
                        }
                    }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Image> list) {
        super.onPostExecute(list);
        progressDialog.dismiss();
    }
}
