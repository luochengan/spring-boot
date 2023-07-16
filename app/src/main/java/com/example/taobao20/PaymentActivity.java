package com.example.taobao20;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobao20.bean.Shopinfo;
import com.example.taobao20.dao.ShopinfoDao;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerPayAdapter payAdapter;
    ShopinfoDao dao;
    List<Shopinfo> list;
    TextView tvsum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        dao = new ShopinfoDao(PaymentActivity.this);
        tvsum=findViewById(R.id.tvmoney);
        list=dao.queryAll();

        //添加适配器
        rv=findViewById(R.id.rv_order);
        rv.setLayoutManager( new LinearLayoutManager(PaymentActivity.this));
        payAdapter =new RecyclerPayAdapter(list);
        rv.setAdapter(payAdapter);

        ImageButton ibback =findViewById(R.id.setting_back);
        Button  buttonorder=findViewById(R.id.button_order);
        ibback.setOnClickListener(v -> finish());

        buttonorder.setOnClickListener(v -> {
            AlertDialog dialog = null;
            AlertDialog.Builder builder= new AlertDialog.Builder(PaymentActivity.this);
            builder.setTitle("支付详情");
            builder.setMessage("您确定购买所有购物车中的商品吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //支付成功后将内容依次删除
                    for (int i=0 ;i<list.size();i++){
                        dao.delete( list.get(i).getId());
                    }

                    Toast.makeText(PaymentActivity.this,"支付成功",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            builder.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(PaymentActivity.this,"订单未支付",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            dialog=builder.create();
            dialog.show();

        });

        toSum();

    }


    private void toSum() {
        for (int i=0;i<list.size();i++){
            Double p = tvsum.getText().toString().equals("") ? 0 : Double.parseDouble(tvsum.getText().toString());
            if (list.get(i).getFlag()==1) p += list.get(i).getSum();
            tvsum.setText("" + new  KeepTwoDecimals().solve(p));//转成字符串
        }
    }
}