package com.example.taobao20;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import com.example.taobao20.bean.Shopinfo;
import com.example.taobao20.dao.ShopinfoDao;


public class Goodsdetial extends AppCompatActivity {

    private ShopinfoDao dao;
    private String name;
    private String introduce;
    private String fan;
    private String price;
    private String payed;
    private String bk1;
    private String bk2;
    private String bk3;
    private String bk4;
    private String dp;
    private int i;
    private int [] icons = {R.drawable.ad1,R.drawable.ad2,R.drawable.ad3,R.drawable.ad4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetial);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
            Intent intent =getIntent();
            name = intent.getStringExtra("name");
            introduce=intent.getStringExtra("introduce");
            fan=intent.getStringExtra("fan");
            price=intent.getStringExtra("price");
            payed=intent.getStringExtra("payed");
            bk1=intent.getStringExtra("bk_1");
            bk2=intent.getStringExtra("bk_2");
            bk3=intent.getStringExtra("bk_3");
            bk4=intent.getStringExtra("bk_4");
            dp=intent.getStringExtra("dp");
            i=intent.getIntExtra("postion",1);
        initView();
    }

    private void initView() {
        ImageButton ibback = findViewById(R.id.detail_back);
        ImageView ivdetail=findViewById(R.id.detail_img);
        TextView tvname=findViewById(R.id.Name2);
        TextView tvintro=findViewById(R.id.introduce2);
        TextView tvbk1=findViewById(R.id.bk_1);
        TextView tvbk2=findViewById(R.id.bk_2);
        TextView tvbk3=findViewById(R.id.bk_3);
        TextView tvbk4=findViewById(R.id.bk_4);
        TextView prices=findViewById(R.id.Money);
        TextView tvfan=findViewById(R.id.fans);
        TextView tvpayed=findViewById(R.id.payed);
        TextView tvdp=findViewById(R.id.dp);
        TextView tv_gwc=findViewById(R.id.tv_jiarugouche);
        TextView tv_shop=findViewById(R.id.tv_shopping);
        ibback.setOnClickListener(v -> {
            finish();
        });
        tvname.setText(name);
        ivdetail.setImageResource(icons[i]);
        tvintro.setText(introduce);
        tvbk1.setText(bk1);
        tvbk2.setText(bk2);
        tvbk3.setText(bk3);
        tvbk4.setText(bk4);
        prices.setText(price);
        tvfan.setText(fan);
        tvpayed.setText(payed);
        tvdp.setText(dp);
        dao =new ShopinfoDao( Goodsdetial.this);
        tv_gwc.setOnClickListener(v -> {
            int icon = icons[i];
            String dianjia = dp;
            String name = introduce;
            String pri = price;
            String sum = price;
            Double p=pri.equals("") ? 0 : Double.parseDouble(pri);
            Double s=sum.equals("") ? 0 : Double.parseDouble(sum);
            p = new KeepTwoDecimals().solve(p);
            s = new KeepTwoDecimals().solve(s);
            //三目运算 balance.equals(“”) 则等于0
            //如果balance 不是空字符串 则进行类型转换
            Shopinfo a = new Shopinfo(icon,dianjia,name,p,s, 1,0);
            Shopinfo aa = dao.queryone(name);
            if(aa==null){
                dao.insert(a);                      // 插入数据库
                Toast.makeText(Goodsdetial.this, "成功加入购物车", Toast.LENGTH_SHORT).show();
                dao.update(a); // 更新数据库
            } else {
                aa.setNumber(aa.getNumber() + 1); // 修改值
                aa.setSum(new KeepTwoDecimals().solve(aa.getSum()+aa.getPrice()));
                Toast.makeText(Goodsdetial.this, "购物车已有，数量增加一", Toast.LENGTH_SHORT).show();
                dao.update(aa); // 更新数据库
            }
        });


        tv_shop.setOnClickListener(v -> {
            Intent intent = new Intent(Goodsdetial.this,PaymentActivity.class);
            startActivity(intent);
        });

    }
}