package com.example.taobao20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobao20.bean.Shopinfo;
import com.example.taobao20.bean.ShopinfoForjson;
import com.example.taobao20.dao.JsonParse;
import com.example.taobao20.dao.ShopinfoDao;

import java.util.List;

public class SouSuo extends AppCompatActivity {
    private ShopinfoDao dao;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<ShopinfoForjson> infolist;

    private int [] icons = {R.drawable.ad1,R.drawable.ad2,R.drawable.ad3,R.drawable.ad4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        infolist = JsonParse.getInstance().getInfoFromJsonshopinfo(SouSuo.this);
        initview();
    }
    private void initview(){
        ImageView imv= findViewById(R.id.imb);
        imv.setOnClickListener(v -> {
            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
            intent.putExtra("data","refresh");
            LocalBroadcastManager.getInstance(SouSuo.this).sendBroadcast(intent);
            sendBroadcast(intent);
            finish();
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv2);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeAdapter = new HomeAdapter();
        recyclerView.setAdapter(homeAdapter);

        ImageView fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(v -> {
            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
            intent.putExtra("data","refresh");
            LocalBroadcastManager.getInstance(SouSuo.this).sendBroadcast(intent);
            sendBroadcast(intent);
            finish();
        });


        dao = new ShopinfoDao(SouSuo.this);
    }

    class HomeAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(SouSuo.this)
                    .inflate(R.layout.sousuo_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.name2.setText(infolist.get(position).getName());
            holder.iv2.setImageResource(
                    icons[position]);
            holder.introduce2.setText(infolist.get(position).getIntroduce());

            holder.fans.setText(infolist.get(position).getFan());
            holder.ju.setText(infolist.get(position).getJu());
            holder.price.setText(infolist.get(position).getPrice());
            holder.payed.setText(infolist.get(position).getPayed());
            holder.bk_1.setText(infolist.get(position).getBk_1());
            holder.bk_2.setText(infolist.get(position).getBk_2());
            holder.bk_3.setText(infolist.get(position).getBk_3());
            holder.bk_4.setText(infolist.get(position).getBk_4());
            holder.dp.setText(infolist.get(position).getDp());
            holder.gwc.setImageResource(R.drawable.shopclick);

            //点击商品图片可跳转到商品详情页,会传递相应的数据
            holder.iv2.setOnClickListener(v -> {
                Intent intent = new Intent(SouSuo.this, Goodsdetial.class);
                intent.putExtra("name",infolist.get(position).getName());
                intent.putExtra("introduce",infolist.get(position).getIntroduce());
                intent.putExtra("fan",infolist.get(position).getFan());
                intent.putExtra("price",infolist.get(position).getPrice());
                intent.putExtra("payed",infolist.get(position).getPayed());
                intent.putExtra("bk_1",infolist.get(position).getBk_1());
                intent.putExtra("bk_2",infolist.get(position).getBk_2());
                intent.putExtra("bk_3",infolist.get(position).getBk_3());
                intent.putExtra("bk_4",infolist.get(position).getBk_4());
                intent.putExtra("dp",infolist.get(position).getDp());
                intent.putExtra("postion",position);
                startActivity(intent);
            });

            holder.gwc.setOnClickListener(v -> {
                int icon = icons[position];
                String dianjia = infolist.get(position).getDp();
                String name = infolist.get(position).getIntroduce();
                String pri = infolist.get(position).getPrice();
                String sum = infolist.get(position).getPrice();
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
                    Toast.makeText(SouSuo.this, "成功加入购物车", Toast.LENGTH_SHORT).show();
                    dao.update(a); // 更新数据库
                } else {
                    aa.setNumber(aa.getNumber() + 1); // 修改值
                    aa.setSum(new KeepTwoDecimals().solve(aa.getSum()+aa.getPrice()));
                    Toast.makeText(SouSuo.this, "购物车已有，数量增加一", Toast.LENGTH_SHORT).show();
                    dao.update(aa); // 更新数据库
                }
                notifyDataSetChanged(); // 刷新界面
            });
        }

        @Override
        public int getItemCount() {
            return infolist.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv2;
        TextView name2;
        TextView introduce2,fans,price;
        TextView ju,payed;
        TextView bk_1,bk_2,bk_3,bk_4;
        TextView dp;
        ImageView gwc;
        public MyViewHolder(View view){
            super(view);
            iv2 = view.findViewById(R.id.iv2);
            name2 = view.findViewById(R.id.name2);
            introduce2 = view.findViewById(R.id.introduce2);
            fans = view.findViewById(R.id.fans);
            ju = view.findViewById(R.id.ju);
            price = view.findViewById(R.id.price);
            payed = view.findViewById(R.id.payed);
            bk_1 = view.findViewById(R.id.bk_1);
            bk_2 = view.findViewById(R.id.bk_2);
            bk_3 = view.findViewById(R.id.bk_3);
            bk_4 = view.findViewById(R.id.bk_4);
            dp = view.findViewById(R.id.dp);
            gwc = view.findViewById(R.id.gwc);

        }
    }

}