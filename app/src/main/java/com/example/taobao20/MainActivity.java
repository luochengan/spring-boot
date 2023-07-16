package com.example.taobao20;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//底部导航栏开始
    //底部五个Fragment
    private HomeFragment    homeFragment;
    private WeiFragment     weiFragment;
    private MessageFragment messageFragment;
    private ShopFragment    shopFragment;
    private MyFragment      myFragment;
    //底部五个Linearlayout
    private LinearLayout    ll_home;
    private LinearLayout    ll_wei;
    private LinearLayout    ll_message;
    private LinearLayout    ll_shop;
    private LinearLayout    ll_my;
    //底部五个ImageView
    private ImageView       iv_home;
    private ImageView       iv_wei;
    private ImageView       iv_message;
    private ImageView       iv_shop;
    private ImageView       iv_my;
    //底部五个标题
    private TextView        tv_home;
    private TextView        tv_wei;
    private TextView        tv_message;
    private TextView        tv_shop;
    private TextView        tv_my;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去除默认标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},0x01);
        initView();
        //初始化底部按钮事件
        initEvent();
        initFragment(1);
    }
    private void initView() {
        ll_home= findViewById(R.id.home);
        ll_wei= findViewById(R.id.weitao);
        ll_message= findViewById(R.id.message);
        ll_shop= findViewById(R.id.shop);
        ll_my= findViewById(R.id.me);

        iv_home= findViewById(R.id.imageButton_home);
        iv_wei= findViewById((R.id.imageButton_weitao));
        iv_message= findViewById(R.id.imageButton_message);
        iv_shop= findViewById(R.id.imageButton_shop);
        iv_my= findViewById(R.id.imageButton_me);

        tv_home= findViewById(R.id.textView_1);
        tv_wei= findViewById(R.id.textView_2);
        tv_message= findViewById(R.id.textView_3);
        tv_shop= findViewById(R.id.textView_4);
        tv_my= findViewById(R.id.textView_5);
    }
    private void initEvent() {
        ll_home.setOnClickListener(this);
        ll_wei.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_shop.setOnClickListener(this);
        ll_my.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //刷新底部图标和标题的颜色
        refreshBottom();
        if(view==ll_home){
            iv_home.setImageResource(R.drawable.taobaoclickn);
            tv_home.setTextColor(Color.rgb(216, 30, 6));
            initFragment(1);
        }else if(view==ll_wei){
            iv_wei.setImageResource(R.drawable.weitaoclick);
            tv_wei.setTextColor(Color.rgb(216, 30, 6));
            initFragment(2);
        }else if (view==ll_message){
            iv_message.setImageResource(R.drawable.xiaoxi_click);
            tv_message.setTextColor(Color.rgb(216, 30, 6));
            initFragment(3);
        }else if (view==ll_shop){
            iv_shop.setImageResource(R.drawable.shopclick);
            tv_shop.setTextColor(Color.rgb(216, 30, 6));
            initFragment(4);
        }else if (view==ll_my){
            iv_my.setImageResource(R.drawable.me_click);
            tv_my.setTextColor(Color.rgb(216, 30, 6));
            initFragment(5);
        }

    }

    private void initFragment(int i) {
        //开始事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的fragment
        hideFragment(transaction);

        switch (i){
            case 1:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.tb_fragment,homeFragment);
                }else{
                    iv_home.setImageResource(R.drawable.taobaoclickn);
                    tv_home.setTextColor(Color.rgb(216, 30, 6));
                    transaction.show(homeFragment);
//                     Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(weiFragment == null){
                    weiFragment = new WeiFragment();
                    transaction.add(R.id.tb_fragment,weiFragment);
                }else{
                    transaction.show(weiFragment);
                    //Toast.makeText(this,"wei",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if(messageFragment == null){
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.tb_fragment,messageFragment);
                }else{
                    transaction.show(messageFragment);
                    //Toast.makeText(this,"message",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                if (shopFragment != null) {
                    transaction.remove(shopFragment);
                    // Toast.makeText(this,"shop",Toast.LENGTH_SHORT).show();
                }
                shopFragment = new ShopFragment();
                transaction.add(R.id.tb_fragment,shopFragment);
                break;
            case 5:
                if(myFragment == null){
                    myFragment = new MyFragment();
                    transaction.add(R.id.tb_fragment,myFragment);
                }else{
                    transaction.show(myFragment);
                }
                break;
        }
        transaction.commit();
    }
    private void refreshBottom() {
        iv_home.setImageResource(R.drawable.taobao);
        iv_wei.setImageResource(R.drawable.weitao);
        iv_message.setImageResource(R.drawable.xiaoxi);
        iv_shop.setImageResource(R.drawable.gouwuche);
        iv_my.setImageResource(R.drawable.me);

        tv_home.setTextColor(Color.rgb(0, 0, 0));
        tv_wei.setTextColor(Color.rgb(0, 0, 0));
        tv_message.setTextColor(Color.rgb(0, 0, 0));
        tv_shop.setTextColor(Color.rgb(0, 0, 0));
        tv_my.setTextColor(Color.rgb(0, 0, 0));
    }

    private void hideFragment(FragmentTransaction transaction){
        if(homeFragment!= null){
            transaction.hide(homeFragment);
        }
        if(weiFragment!= null){
            transaction.hide(weiFragment);
        }
        if(messageFragment!= null){
            transaction.hide(messageFragment);
        }
        if(shopFragment!= null){
            transaction.hide(shopFragment);
        }
        if(myFragment!=null){
            transaction.hide(myFragment);
        }
    }
}

