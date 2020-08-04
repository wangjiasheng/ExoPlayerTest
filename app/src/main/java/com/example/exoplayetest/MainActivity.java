package com.example.exoplayetest;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.wjs.holder.ExoplayerImp;
import com.wjs.holder.MediaPlayerImpl;
import com.wjs.holder.PlayerBase;
import com.wjs.holder.PlayerProgressbarListener;
import com.wjs.holder.SurfaceViewHolder;
public class MainActivity extends AppCompatActivity implements PlayerProgressbarListener {
    private SurfaceView surfaceView; //播放区
    private ProgressBar mProgressBar;
    public SurfaceViewHolder manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.i("wangjiasheng","onCreate");
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mProgressBar=findViewById(R.id.mProgressBar);
        manager=new SurfaceViewHolder(this,surfaceView,this);
        PlayerBase playerBase1=new MediaPlayerImpl();
        PlayerBase playerBase2=new ExoplayerImp();
        manager.initPlayer(playerBase1);
        manager.playerUrl(Constant.ccty1);
    }
    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.release();
    }
}