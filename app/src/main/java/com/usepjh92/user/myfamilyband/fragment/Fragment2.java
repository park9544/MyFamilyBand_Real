package com.usepjh92.user.myfamilyband.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.adapter.Frag2RecyclerAdapter;
import com.usepjh92.user.myfamilyband.item.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Fragment2 extends android.support.v4.app.Fragment {

    ArrayList<Item> items = new ArrayList<>();
    Frag2RecyclerAdapter adapter;
    RecyclerView recyclerView;
    String loadUrl = "http://neworld.dothome.co.kr/android/loadDB.php";

    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.page2_recycler);

        loadDB();

        adapter = new Frag2RecyclerAdapter(items, getActivity());

        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager recycler = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(recycler);

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);





        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDB();
                refreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
        }
    };

    //DB 에서 텍스트와 이미지를 불러오는 작업..
    public void loadDB() {


        new Thread() {

            @Override
            public void run() {

                try {
                    URL url = new URL(loadUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setUseCaches(false);

                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = new String();

                    while (line != null) {
                        buffer.append(line);
                        line = reader.readLine();
                    }
                    String str = buffer.toString();
                    str = str.replaceAll("<br/>" , "");
                    String[] rows = str.split(";");
                    items.clear();
                    for (String row : rows) {

                        String[] datas = row.split("&");

                            if (datas.length == 2){
                                String title = datas[0];
                                String desc = datas[1];
                                items.add(new Item( title , desc));

                            }else if (datas.length == 3){
                                String title = datas[0];
                                String desc = datas[1];
                                String imgUrl = datas[2];
                                items.add(new Item(title , desc , imgUrl));
                            }

                        handler.sendEmptyMessage(0);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
