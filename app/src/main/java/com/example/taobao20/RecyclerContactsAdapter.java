package com.example.taobao20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobao20.bean.HomeBottomiteminfo;

import java.util.List;

public class RecyclerContactsAdapter extends RecyclerView.Adapter<RecyclerContactsAdapter.MyViewHolder> {

    private View itemView;

    private List<String> names;
    private List<List<String>> details;

    public RecyclerContactsAdapter(List<String> names, List<List<String>> details) {
        this.names=names;
        this.details=details;
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_contacts, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.touxiang.setImageResource(R.drawable.touxiang);
        holder.name.setText(names.get(position));
        String s=names.get(position);
        for(int i=0;i<details.get(position).size();i++)
        {
            holder.phone.setText(details.get(position).get(i)+" ");
        }
        holder.tijiao.setText("添加");
        holder.tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(itemView.getContext(), "成功添加"+s+"为淘宝好友", Toast.LENGTH_LONG).show();
                holder.tijiao.setText("已添加");
                holder.tijiao.setEnabled(false);
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
        ImageView touxiang;
        Button tijiao;
        public MyViewHolder(View view){
            super(view);
            touxiang = view.findViewById(R.id.contacts_icon);
            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            tijiao = view.findViewById(R.id.add_newfriends);
        }
    }

}
