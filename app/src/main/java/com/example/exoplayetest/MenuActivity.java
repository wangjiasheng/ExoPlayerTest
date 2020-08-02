package com.example.exoplayetest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuActivity extends MainActivity {
    RecyclerView mRecycleView;
    List<MovieBean> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup viewRoot=findViewById(android.R.id.content);
        View view= LayoutInflater.from(this).inflate(R.layout.layout_list,null,true);
        mRecycleView=view.findViewById(R.id.mRecycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        ViewGroup viewGroup=((ViewGroup) viewRoot.getChildAt(0));
        viewGroup.getChildCount();
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(view,2,params);
        viewGroup.getChildCount();


        new Thread(){
            @Override
            public void run() {
                HttpURLConnection urlConnection;
                InputStream inputStream;
                InputStreamReader inputStreamReader;
                BufferedReader bufferedReader;
                try {
                    urlConnection= (HttpURLConnection) new URL("https://ys.xitaofan.com/zbtv/zb.txt").openConnection();
                    inputStream = urlConnection.getInputStream();
                    inputStreamReader=new InputStreamReader(inputStream);
                    bufferedReader=new BufferedReader(inputStreamReader);
                    String readStr=null;
                    while((readStr=bufferedReader.readLine())!=null){
                        Pattern pattern=Pattern.compile("(\\S+)(?=,((http|https)://\\S+\\.m3u8))\\S+");
                        Matcher matcher=pattern.matcher(readStr);
                        if(matcher.matches()){
                            list.add(new MovieBean(matcher.group(1),matcher.group(2)));
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecycleView.setAdapter(new MyAdapter());
                    }
                });
            }
        }.start();
    }
    public class MyAdapter extends RecyclerView.Adapter<MyVoidHolder>{
        @NonNull
        @Override
        public MyVoidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            View view=inflater.inflate(R.layout.list_item,parent,false);
            return new MyVoidHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull MyVoidHolder holder, int position) {
            holder.binData(list.get(position));
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    public class MyVoidHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public MyVoidHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.mText);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Toast.makeText(MenuActivity.this,list.get(position).getUrl(),Toast.LENGTH_SHORT).show();
                    manager.playerUrl(list.get(position).getUrl());
                }
            });
        }
        public void binData(MovieBean bean){
            mTextView.setText(bean.getName());
        }
    }
    public class MovieBean{
        private String name;
        private String url;

        public MovieBean(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }
}
