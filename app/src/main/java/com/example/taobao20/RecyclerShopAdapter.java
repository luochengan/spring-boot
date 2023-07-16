package com.example.taobao20;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobao20.bean.Shopinfo;
import com.example.taobao20.dao.ShopinfoDao;


import java.util.ArrayList;
import java.util.List;

public class RecyclerShopAdapter extends RecyclerView.Adapter<RecyclerShopAdapter.MyViewHolder> {

    private View itemView;
    // 需要适配的数据集合
    private List<Shopinfo> list;
    //购物车文本监听
    private TextView shop_shop;
    //结算按钮
    private Button shop_jiesuan;
    //总金额文本
    private TextView tv_sum;
    //全选按钮
    private CheckBox all_checked ;
    private Context context;

    public RecyclerShopAdapter(List<Shopinfo> PreList, TextView shop_shop, Button shop_jiesuan, TextView tv_sum, CheckBox all_checked, Context context) {
        this.list = PreList;
        this.context = context;
//        layoutInflater = LayoutInflater.from(Precontext);
        this.shop_shop = shop_shop;
        this.shop_jiesuan = shop_jiesuan;
        this.tv_sum = tv_sum;
        this.all_checked = all_checked;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shop, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ShopinfoDao dao = new ShopinfoDao(itemView.getContext());
        Shopinfo a = list.get(position);
        if (position == 0)
            holder.dianTv.setText(a.getDianjia() + ">");
        else {
            if (list.get(position - 1).getDianjia().equals(a.getDianjia()))
                holder.dianTv.setText("");
            else
                holder.dianTv.setText(a.getDianjia() + ">");
        }
        holder.nameTv.setText(a.getName());
        holder.priceTv.setText(a.getSum() + "");
        holder.numberTv.setText(a.getNumber() + "");
        holder.iconTv.setImageResource(a.getIcon());

        shop_jiesuan.setOnClickListener(v -> {
            if (shop_jiesuan.getText().toString().equals("删除") && holder.checkTv.isChecked()) {
                list.remove(a);          // 从集合中删除
                dao.delete(a.getId()); // 从数据库中删除
                notifyDataSetChanged();// 刷新界面
            }
            if (shop_jiesuan.getText().toString().equals("结算")&&holder.checkTv.isChecked()&&!list.isEmpty()){
                //结算监听
                shop_jiesuan.setOnClickListener(v1 -> {
                    notifyDataSetChanged();// 刷新界面
                    Toast.makeText(v1.getContext(), "正在跳转到支付界面", Toast.LENGTH_SHORT).show();
                    Log.i("跳转过程", "onBindViewHolder:。。。。。。ing ");
                    Intent intent =new Intent(v1.getContext(),PaymentActivity.class);
                    intent.putExtra("summoney",tv_sum.getText());
                    context.startActivity(intent);
                    Log.i("跳转成功", "YES");
                });
            }
        });

        //加号的点击事件触发的方法
        holder.upTv.setOnClickListener(v -> {
            a.setNumber(a.getNumber() + 1); // 修改值
            a.setSum(new KeepTwoDecimals().solve(a.getSum() + a.getPrice()));
            if (holder.checkTv.isChecked()) {
                Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
                p += a.getPrice();
                tv_sum.setText("" + new KeepTwoDecimals().solve(p));
            }
            notifyDataSetChanged(); // 刷新界面
            dao.update(a); // 更新数据库
        });
        //减号的点击事件触发的方法
        holder.downTv.setOnClickListener(v -> {
            if (a.getNumber() < 2) {
                DialogInterface.OnClickListener listener =
                        (dialog, which) -> {
                            list.remove(a);          // 从集合中删除
                            dao.delete(a.getId()); // 从数据库中删除
                            if (holder.checkTv.isChecked()) {
                                Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
                                p -= a.getPrice();
                                tv_sum.setText("" + new KeepTwoDecimals().solve(p));
                            }
                            notifyDataSetChanged();// 刷新界面
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext()); // 创建对话框
                builder.setTitle("确定要删除吗?");                    // 设置标题
                // 设置确定按钮的文本以及监听器
                builder.setPositiveButton("确定", listener);
                builder.setNegativeButton("取消", null);         // 设置取消按钮
                builder.show(); // 显示对话框
            } else {
                if (holder.checkTv.isChecked()) {
                    Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
                    p -= a.getPrice();
                    tv_sum.setText("" + new KeepTwoDecimals().solve(p));
                }
                a.setNumber(a.getNumber() - 1);
                a.setSum(new KeepTwoDecimals().solve(a.getSum() - a.getPrice()));

                notifyDataSetChanged();
                dao.update(a);
            }
        });
        //删除图片的点击事件触发的方法
        holder.deleteTv.setOnClickListener(v -> {
            //删除数据之前首先弹出一个对话框
            DialogInterface.OnClickListener listener =
                    (dialog, which) -> {
                        Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
                        p -= a.getSum();
                        tv_sum.setText("" + new KeepTwoDecimals().solve(p));
                        list.remove(a);          // 从集合中删除
                        dao.delete(a.getId()); // 从数据库中删除
                        notifyDataSetChanged();// 刷新界面
                    };
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext()); // 创建对话框
            builder.setTitle("确定要删除吗?");                    // 设置标题
            // 设置确定按钮的文本以及监听器
            builder.setPositiveButton("确定", listener);
            builder.setNegativeButton("取消", null);         // 设置取消按钮
            builder.show(); // 显示对话框
        });

        if (a.getFlag() == 1)
            holder.checkTv.setChecked(true);
        else
            holder.checkTv.setChecked(false);

        //选择商品监听事件
        holder.checkTv.setOnClickListener(v -> {
            if(holder.checkTv.isChecked())
            {
                a.setFlag(1);
                dao.update(a);
                Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
                if (!all_checked.isChecked()) p += a.getSum();
                tv_sum.setText("" + new KeepTwoDecimals().solve(p));
                int cnt = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getFlag() == 1)
                        cnt++;
                }
                if (cnt == list.size())
                    all_checked.setChecked(true);
            } else

            {
                a.setFlag(0);
                dao.update(a);
                Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
                p -= a.getSum();
                tv_sum.setText("" + new KeepTwoDecimals().solve(p));
                all_checked.setChecked(false);
//                        notifyDataSetChanged();
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dianTv,nameTv,priceTv,numberTv;
        ImageView iconTv,upTv,downTv,deleteTv;
        CheckBox checkTv;
        public MyViewHolder(View view){
            super(view);
            dianTv = view.findViewById(R.id.shop_dianpu);
            iconTv =  view.findViewById(R.id.shop_gecimage);
            nameTv = view.findViewById(R.id.shop_gwcname);
            priceTv =  view.findViewById(R.id.shop_gwcprice);
            numberTv =  view.findViewById(R.id.shop_gwcnum);
            upTv =  view.findViewById(R.id.shop_gwcadd);
            downTv =  view.findViewById(R.id.shop_gwcsub);
            deleteTv= view.findViewById(R.id.shop_gwcdel);
            checkTv= view.findViewById(R.id.shop_checked);
        }
    }

}