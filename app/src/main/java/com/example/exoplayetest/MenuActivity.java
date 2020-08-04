package com.example.exoplayetest;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import static android.view.KeyEvent.ACTION_DOWN;

public class MenuActivity extends MainActivity {
    RecyclerView mRecycleView;
    View contentView;
    List<MovieBean> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup viewRoot=findViewById(android.R.id.content);
        contentView= LayoutInflater.from(this).inflate(R.layout.layout_list,null,true);
        mRecycleView=contentView.findViewById(R.id.mRecycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        ViewGroup viewGroup=((ViewGroup) viewRoot.getChildAt(0));
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(contentView,2,params);
        contentView.setVisibility(View.GONE);

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
                            if(list.size()==0) {
                                list.add(new MovieBean(matcher.group(1), matcher.group(2)));
                            }else{
                                list.add(new MovieBean(matcher.group(1), matcher.group(2)));
                            }
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
            holder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButton();
                    manager.playerUrl(list.get(position).getUrl());
                }
            });
            holder.binData(list.get(position));
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    public void setFocus(View view,boolean bool){
        if(bool){
            view.setClickable(false);
            view.setFocusableInTouchMode(true);
            view.setFocusable(true);
            view.requestFocus();
        }else{
            view.setClickable(true);
            view.setFocusableInTouchMode(false);
            view.setFocusable(false);
        }
    }
    public class MyVoidHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        private View contentView;
        public MyVoidHolder(@NonNull View itemView) {
            super(itemView);
            contentView=itemView;
            mTextView=itemView.findViewById(R.id.mText);
            mTextView.setTag(itemView);
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(ACTION_DOWN == event.getAction()){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_MENU:
                    toggleButton();
                    return true;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 切换Menu显示状态
     */
    public void toggleButton(){
        if(contentView.getVisibility()==View.VISIBLE){
            contentView.setVisibility(View.GONE);
        }else{
            contentView.setVisibility(View.VISIBLE);
        }
    }
}
