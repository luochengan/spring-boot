package com.example.taobao20;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;//中部菜单
    private RecyclerView bottomRecyclerView;//底部信息
    private RecyclerBottomAdapter recyclerBottomAdapter;
    private HomeAdapter mAdapter;//中部菜单的适配器

    private final int[] img={ R.drawable.r1, R.drawable.r2, R.drawable.r3,R.drawable.r4,R.drawable.r5,R.drawable.r6,R.drawable.r7,R.drawable.r8,R.drawable.r9,R.drawable.r10,R.drawable.r10,R.drawable.r1, R.drawable.r2,R.drawable.r3,R.drawable.r4};
    private final String[] info={ "天猫 U先","今日爆款","天猫国际","芭芭农场","天猫超市","充值中心","淘鲜达","领淘金币","阿里拍卖","分类","分类", "天猫 U先","今日爆款","天猫国际","芭芭农场"};
    private TextView mainTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        initview();
        return view;
    }

    private void initview() {
        mainTV = view.findViewById(R.id.tv_timer);
        new TimeThread().start();

        ViewFlipper flipper1 = view.findViewById(R.id.flipper1);
        ViewFlipper flipper2 =view.findViewById(R.id.flipper2);
        flipper1.startFlipping();
        flipper2.startFlipping();

        mRecyclerView= view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //设置底部商品页，json+recyclerview
        bottomRecyclerView=view.findViewById(R.id.rv_homepagebottom);
        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerBottomAdapter= new RecyclerBottomAdapter();
        bottomRecyclerView.setAdapter(recyclerBottomAdapter);




        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);//数字为行数或列数
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置为横向滑动
        mRecyclerView.setLayoutManager(manager);

        TextView tv1= view.findViewById(R.id.tv1);

        ImageButton imageButtonss;
        imageButtonss= view.findViewById(R.id.sysbtn);
        imageButtonss.setOnClickListener(view -> {
            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
            builder.setTitle("正在检索商品信息（样例）");
            builder.setMessage("是否确定购买？");
            //点击确定之后，设置监听事件来获取并在logcat中输出信息
            builder.setPositiveButton("确定", (dialog12, which) -> {
                AlertDialog dialog1;
                builder2.setMessage("购买成功!");
                builder2.setNegativeButton("好的", (dialog2, i) -> dialog2.dismiss());
                dialog1 = builder2.create();
                dialog1.show();
            }).setNegativeButton("取消", (dialog13, which) -> dialog13.dismiss());
            dialog = builder.create();
            dialog.show();
        });
        tv1.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SouSuo.class);
            startActivity(intent);
        });







    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_item, parent, false));
            }

            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.imgs.setImageResource(img[position]);
                holder.infos.setText(info[position]);
            }
            public int getItemCount() {
                return img.length;
            }
            class MyViewHolder extends RecyclerView.ViewHolder{
                ImageView imgs;
                TextView infos;
                public MyViewHolder(@NonNull View view) {
                    super(view);
                    imgs= view.findViewById(R.id.img);
                    infos= view.findViewById(R.id.info);

                }

            }


    }

    private class TimeThread extends Thread{
        @Override
        public void run() {
            super.run();
            do try {
                Thread.sleep(1000);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } while (true);
        }
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    DateFormat df = new SimpleDateFormat("HH:mm:ss");
                    Date d1;
                    try {
                        d1 = df.parse(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                        Date d2 = df.parse("24:00:00");
                        assert d2 != null;
                        long diff = d2.getTime() - Objects.requireNonNull(d1).getTime();//获取当前时间与24:00:00之间的差值，单位为毫秒
                        long days = diff / (1000 * 60 * 60 * 24);
                        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000 * 60 * 60);
                        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000 * 60 * 60))/(1000 * 60);//进行一些简单的单位换算
                        long seconds= (diff-days*(1000 * 60 * 60 * 24)-hours*(1000 * 60 * 60)-minutes*(1000 * 60))/1000;
                        mainTV.setText( hours+"时"+minutes+"分"+seconds+"秒");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
            return false;
        }
    });

}