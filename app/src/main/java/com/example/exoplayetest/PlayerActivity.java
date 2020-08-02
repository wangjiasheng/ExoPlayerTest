package com.example.exoplayetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PlayerActivity extends MainActivity {
    Map<Integer,String> permission=new HashMap<Integer, String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,WRITE_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this).setTitle("警告").setMessage("没有权限就不能使用").setNegativeButton("确定使用权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(PlayerActivity.this,new String[]{WRITE_EXTERNAL_STORAGE}, 100);
                        dialogInterface.dismiss();
                    }
                }).setNeutralButton("退出App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                    }
                }).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE}, 100);
            }
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
