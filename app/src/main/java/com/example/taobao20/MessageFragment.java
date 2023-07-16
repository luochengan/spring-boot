package com.example.taobao20;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;

    private final int [] pic={R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.activity,R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.activity,R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.activity,R.drawable.lianxiren,R.drawable.lianxiren,R.drawable.lianxiren};
    private final String [] date ={ "星期一","星期二","昨天","前天","22/11/02","22/11/09","22/09/08","星期二","昨天","前天","22/11/02","星期二","昨天","前天","22/11/02"};
    private final String [] name={"花花公子专卖店","墨点专卖店","交易物流","活动优惠","李宁专卖店","程序员帮帮","皇家惠人浮云专卖店","墨点专卖店","交易物流","活动优惠","李宁专卖店","墨点专卖店","交易物流","活动优惠","李宁专卖店"};
    private final String [] talk={"123","好的亲亲，再见呢bye","KOOK","亲亲，对产品满意的话，现在小店有晒图活动可以干阿八八八八八八八八八八八","8亲亲亲亲亲亲8888886","亲亲亲亲亲亲亲亲亲亲亲亲亲亲88888886","88亲亲亲亲亲亲888886","好的亲亲，再见呢bye","KOOK","亲亲，对产品满意的话，现在小店有晒图活动可以干阿八八八八八八八八八八八","8亲亲亲亲亲亲8888886","8亲亲亲亲亲亲8888886","亲亲亲亲亲亲亲亲亲亲亲亲亲亲88888886","88亲亲亲亲亲亲888886","好的亲亲，再见呢bye"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);

        initview();


        return view;
    }

    private void initview() {
        mRecyclerView= view.findViewById(R.id.rvtalk);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        Button btnsearch = view.findViewById(R.id.search_addressbook);
        btnsearch.setOnClickListener(view -> {
            Intent intent =new Intent(getActivity(),Contact.class);
            startActivity(intent);
        });
    }

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        @NonNull
        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.recycler_chat,parent,false));
        }
        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
            holder.imgt.setImageResource(pic[position]);
            holder.talk.setText(talk[position]);
            holder.name.setText(name[position]);
            holder.week.setText(date[position]);
        }
        @Override
        public int getItemCount() {
            return pic.length;
        }
        public class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView imgt;
            TextView name;
            TextView talk;
            TextView week;
            public MyViewHolder(@NonNull View view) {
                super(view);
                imgt =view.findViewById(R.id.iconm);
                name=view.findViewById(R.id.textname);
                talk=view.findViewById(R.id.texttalk);
                week=view.findViewById(R.id.week);
            }
        }
    }


}