package com.example.exoplayetest;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import androidx.recyclerview.widget.RecyclerView;


import com.wjs.upatetask.NetworkTask;
import com.wjs.upatetask.URLBuilder;


public class UpdateActivity extends PlayerActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkTask task=new NetworkTask(this,new URLBuilder(this).builderHost("192.168.1.12").incremental_update(true).build());
        task.execute();

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getAction()){
            case KeyEvent.KEYCODE_MENU:
               View view = findViewById(R.id.content);
                return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
