package com.example.dong.comic.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.dong.comic.AsyncTaskListChap;
import com.example.dong.comic.ImageActivity;
import com.example.dong.comic.R;
import com.example.dong.comic.common.AppStatus;
import com.example.dong.comic.interFace.IListChap;
import com.example.dong.comic.model.Chap;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DONG on 10-Apr-17.
 */

public class ListChapFragment extends Fragment implements IListChap, SearchView.OnQueryTextListener {
    private static final String URL = "http://1.truyentranhmoi.com/o-long-vien-linh-vat-song/";
    ListView listview;
    ArrayList<Chap> listChap = new ArrayList<>();
    ArrayAdapter<Chap> arrayAdapter;
    PullRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_listchap,container,false);
        if (AppStatus.getInstance(getContext()).isOnline()) {

            AsyncTaskListChap asyncTacskListChap = new AsyncTaskListChap(getContext(),this);
            asyncTacskListChap.execute(URL);
            listChap = asyncTacskListChap.getListchap();


            addControl(view);
            addEvent();

            swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            arrayAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 2000);
                }
            });

        } else {

            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.title_connect)
                    .setMessage(R.string.mess)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        return view;
    }

    private void addEvent() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String href = listChap.get(position).getHref();
                Intent intent = new Intent(getContext(), ImageActivity.class);
                intent.putExtra("href", href);
                startActivity(intent);

            }
        });

    }

    private void addControl(View view) {
        swipeRefreshLayout= (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        listview = (ListView) view.findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<Chap>(getContext(), R.layout.mytextview, listChap);

        listview.setAdapter(arrayAdapter);

    }

    @Override
    public void restoringPreferences1() {
        SharedPreferences pre = getContext().getSharedPreferences("my_data", MODE_PRIVATE);
        final String chapCurent = pre.getString("chapCurent", "");
        if (chapCurent != "") {
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setTitle(R.string.title_dialog);
            b.setMessage(R.string.message_dialog);
            b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getContext(), ImageActivity.class);
                    intent.putExtra("href", chapCurent);
                    startActivity(intent);
                }
            });
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


    @Override
    public void processFinish() {
        arrayAdapter.notifyDataSetChanged();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        // Retrieve the SearchView and plug it into SearchManager
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//
//
//        SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getContext().getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(this);
//
//        return true;
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        arrayAdapter.getFilter().filter(newText);
        return true;
    }
}
