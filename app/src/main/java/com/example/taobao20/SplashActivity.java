package com.example.taobao20;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //使得界面全屏显示
        View decorView=getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //隐藏标题栏
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null) actionBar.hide();

        TextView tv1= findViewById(R.id.welcome_text);

        handler.postDelayed(() -> {
            tv1.setOnClickListener(v -> {
                tv1.setText(" by 202034070238 罗成安 ");
            });
            tv1.setText(" by 202034070238 罗成安 ");
        },1500); //设置两秒后修改信息

        handler.postDelayed(() -> {
            Intent intent=new Intent();
            intent.setClass(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        },3000); //设置三秒后跳转

    }
}