package com.example.taobao20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobao20.bean.Shopinfo;
import com.example.taobao20.dao.ShopinfoDao;

import java.util.List;

public class RecyclerPayAdapter extends RecyclerView.Adapter<RecyclerPayAdapter.MyViewHolder> {
    private List<Shopinfo> list;
    private View itemView;
    public  RecyclerPayAdapter(List<Shopinfo> list){
        this.list=list;
    }
    @NonNull
    @Override
    public RecyclerPayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_orders, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPayAdapter.MyViewHolder holder, int position) {
        Shopinfo a= list.get(position);
        holder.ivicon.setImageResource(a.getIcon());
        holder.tvname.setText(a.getName());
        holder.tvdp.setText(a.getDianjia());
        holder.tvnumber.setText(a.getNumber()+"");
        holder.tvprice.setText(a.getPrice()+"");
        holder.tvxiaoji.setText(a.getSum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivicon;
        TextView tvdp;//店铺
        TextView tvnumber;//数量
        TextView tvname;//介绍
        TextView tvxiaoji;//小计
        TextView tvprice;
        public MyViewHolder(@NonNull View v) {
            super(v);
            tvdp=v.findViewById(R.id.tvshop);
            tvnumber=v.findViewById(R.id.tvnumber);
            tvname=v.findViewById(R.id.tvname);
            tvxiaoji=v.findViewById(R.id.tvxiaoji);
            ivicon=v.findViewById(R.id.ivicon);
            tvprice=v.findViewById(R.id.tvprice);
        }

    }
}
