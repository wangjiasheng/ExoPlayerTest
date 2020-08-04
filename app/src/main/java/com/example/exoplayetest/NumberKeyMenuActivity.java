package com.example.exoplayetest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID;

public class NumberKeyMenuActivity extends MenuActivity {
    TextView textView;
    int keyCode=-1;
    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 314232332:
                    if(keyCode==-1) {
                        keyCode = msg.arg1;
                    }else{
                        if(keyCode*10+msg.arg1<10000) {
                            keyCode = keyCode * 10 + msg.arg1;
                        }
                    }
                    showKeyCodeView();
                    break;
                case 812330500:
                    if(keyCode<list.size()&&keyCode!=-1){
                        manager.playerUrl(list.get(keyCode).getUrl());
                    }
                    keyCode=-1;
                    hideKeyCodeView();
                    break;
            }
        }
    };
    public void hideKeyCodeView(){
        textView.setText("");
        textView.setVisibility(View.GONE);
    }
    public void showKeyCodeView(){
        textView.setText(String.valueOf(keyCode));
        textView.setVisibility(View.VISIBLE);
    }
    public void redirectVideo(){

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup viewRoot=findViewById(android.R.id.content);
        ViewGroup viewGroup=((ViewGroup) viewRoot.getChildAt(0));


        textView=new TextView(this);
        textView.setTextSize(20);
        textView.setBackgroundColor(Color.GREEN);
        ConstraintLayout.LayoutParams params=new ConstraintLayout.LayoutParams(100, 40);
        params.bottomToBottom=PARENT_ID;
        params.endToEnd=PARENT_ID;
        params.startToStart=PARENT_ID;
        params.setMargins(0,0,0,50);
        textView.setGravity(Gravity.BOTTOM|Gravity.CENTER);
        viewGroup.addView(textView,3,params);
        textView.setVisibility(View.GONE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_0:
                case KeyEvent.KEYCODE_1:
                case KeyEvent.KEYCODE_2:
                case KeyEvent.KEYCODE_3:
                case KeyEvent.KEYCODE_4:
                case KeyEvent.KEYCODE_5:
                case KeyEvent.KEYCODE_6:
                case KeyEvent.KEYCODE_7:
                case KeyEvent.KEYCODE_8:
                case KeyEvent.KEYCODE_9:
                    Message messageKey = handler.obtainMessage(314232332);
                    messageKey.arg1=event.getKeyCode()-7;
                    handler.sendMessage(messageKey);
                    handler.removeMessages(812330500);
                    handler.sendEmptyMessageDelayed(812330500,1500);
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
