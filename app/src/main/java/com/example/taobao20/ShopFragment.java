package com.example.taobao20;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobao20.bean.Shopinfo;
import com.example.taobao20.dao.ShopinfoDao;

import java.util.List;

/**
 * 参考
 * 202034070235
 * 张雪晨
 */
public class ShopFragment extends Fragment {

    // 需要适配的数据集合
    private List<Shopinfo> list;

    // 数据库增删改查操作类
    private ShopinfoDao dao;
    //    private Shopinfo pre = new Shopinfo();
//    private HashMap<String,Boolean> flag_dianjia = new HashMap<String,Boolean>();
    private View view;
    // 适配器
    private RecyclerShopAdapter adapter;
    // RecyclerView
    private RecyclerView recyclerView;
    //全选按钮
    private CheckBox all_checked ;
    //总金额文本
    private TextView tv_sum;
    //购物车文本监听
    private TextView shop_shop;
    //管理文本
    private TextView shop_manager;
    //结算按钮
    private Button shop_jiesuan;
    //标记是否点击删除
    private Boolean del_flag = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_shop, container, false);
        //购物车管理监听
        shop_manager = (TextView) view.findViewById(R.id.shop_manage);
        //购物车总金额监听
        tv_sum = (TextView) view.findViewById(R.id.shop_sum);
        //全选按钮监听
        all_checked = (CheckBox) view.findViewById(R.id.shop_all);
        //结算按钮监听
        shop_jiesuan = (Button) view.findViewById(R.id.shop_jiesuan);
        //购物车文本监听
        shop_shop = (TextView) view.findViewById(R.id.shop_shop);
        dao = new ShopinfoDao(view.getContext());
        // 从数据库查询出所有数据
        list = dao.queryAll();
        initview();
        recyclerView = (RecyclerView) view.findViewById(R.id.accountRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter = new RecyclerShopAdapter(getActivity().getApplicationContext(),list,shop_shop,shop_jiesuan,tv_sum,all_checked);
        adapter = new RecyclerShopAdapter(list,shop_shop,shop_jiesuan,tv_sum,all_checked,getContext());

        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //购物车文本监听
        shop_shop.setOnClickListener(v -> {
            shop_jiesuan.setText("结算");
//                shop_jiesuan.setTextScaleX(1);
//                shop_jiesuan.setBackground(getResources().getDrawable(R.drawable.shape));
//                ViewGroup.LayoutParams lp_tv1=(ViewGroup.LayoutParams)shop_jiesuan.getLayoutParams();
//                lp_tv1.height=130;
//                lp_tv1.width=300;
//                shop_jiesuan.setLayoutParams(lp_tv1);
        });
        //管理监听
        shop_manager.setOnClickListener(v -> {
            shop_jiesuan.setText("删除");
//                shop_jiesuan.setTextScaleX(1);
//                shop_jiesuan.setBackground(getResources().getDrawable(R.drawable.shape));
//                ViewGroup.LayoutParams lp_tv1= shop_jiesuan.getLayoutParams();
//                lp_tv1.height=130;
//                lp_tv1.width=300;
//                shop_jiesuan.setLayoutParams(lp_tv1);
        });
        //全选按钮监听
        all_checked.setOnClickListener(v -> {
            if(all_checked.isChecked())
            {
                for (int i=0;i<list.size();i++){
                    list.get(i).setFlag(1);
                    dao.update(list.get(i));
                }
                adapter.notifyDataSetChanged();// 刷新界面
                tv_sum.setText(""+new  KeepTwoDecimals().solve(dao.querySum()));
//                    Toast.makeText(getActivity(), "quanxuan", Toast.LENGTH_SHORT).show();
            } else {
                for (int i=0;i<list.size();i++){
                    list.get(i).setFlag(0);
                    dao.update(list.get(i));
                }
//                    tv_sum.setText(""+new   KeepTwoDecimals().solve(dao.querySum()));
                tv_sum.setText("0.0");
                adapter.notifyDataSetChanged();// 刷新界面
//                    Toast.makeText(getActivity(), "buxuan", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }
    private void initview() {
        for (int i=0;i<list.size();i++){
            Double p = tv_sum.getText().toString().equals("") ? 0 : Double.parseDouble(tv_sum.getText().toString());
            if (list.get(i).getFlag()==1) p += list.get(i).getSum();
//                        Log.e(""+a.getSum(), ""+a.getSum()+" "+a.getName(),null );
            tv_sum.setText("" + new   KeepTwoDecimals().solve(p));
        }
        
    }
}