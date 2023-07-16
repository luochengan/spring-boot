package com.example.taobao20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobao20.bean.HomeBottomiteminfo;
import com.example.taobao20.dao.JsonParse;

import java.util.List;

public class RecyclerBottomAdapter extends RecyclerView.Adapter<RecyclerBottomAdapter.MyViewHolder> {

    private List<HomeBottomiteminfo> infolist;

    @NonNull
    @Override
    public RecyclerBottomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        infolist = JsonParse.getInstance().getInfoFromJsonbottomitem(parent.getContext());
//        Log.i("LISTLONG", "onCreateViewHolder: "+infolist.size());
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_homepagebottomitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBottomAdapter.MyViewHolder holder, int position) {
        holder.title.setText(infolist.get(position).getTitle());
        holder.price.setText(infolist.get(position).getPrice());
        if (infolist.get(position).getTitle().equals("聚划算")){
            holder.imv.setImageResource(R.drawable.down1);
        }else if(infolist.get(position).getTitle().equals("元气森林")){
            holder.imv.setImageResource(R.drawable.down2);
        }else if(infolist.get(position).getTitle().equals("修猫咪")){
            holder.imv.setImageResource(R.drawable.down3);
        }else if(infolist.get(position).getTitle().equals("双11活动")){
            holder.imv.setImageResource(R.drawable.down4);
        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView price;
        ImageView imv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            price =itemView.findViewById(R.id.tv_price);
            imv =itemView.findViewById(R.id.imageView7);
        }

    }
}
