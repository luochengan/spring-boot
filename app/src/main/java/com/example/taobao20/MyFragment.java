package com.example.taobao20;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taobao20.dao.SPSave;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {
    private View view;
    private TextView tvid;
    private TextView tvname;
    private TextView tvset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my, container, false);
        initview();

        //检索用户登录信息
        Map<String ,String> userinfo= SPSave.getUserInfo(getActivity());
        if (userinfo!=null){
            tvid.setText("账户名"+userinfo.get("account"));
            tvname.setText("黄澄澄的紫根牡丹");//淘宝默认昵称
//            tvpwd.setText(userinfo.get("password"));
        }



        return view;
    }
    private void initview() {
        TextView tvlogin= view.findViewById(R.id.touxiang);
        tvid= view.findViewById(R.id.textViewid);
        tvname= view.findViewById(R.id.textViewname);
        tvset = view.findViewById(R.id.textView4);
        tvlogin.setOnClickListener(view -> {
            Intent intent= new Intent(getActivity(), Login.class);
            startActivity(intent);
        });

        tvset.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
        });
//        tvname.setOnClickListener(v -> {
//            //从shared preferences 检查用户登录信息
//            Map<String ,String> userinfo= SPSave.getUserInfo(getActivity());
//            if (userinfo!=null){
//                tvid.setText("账户名"+userinfo.get("account"));
//                tvname.setText("黄澄澄的紫根牡丹");//淘宝默认昵称
////            tvpwd.setText(userinfo.get("password"));
//            }
//        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==1&&resultCode==2){
//            String id = data.getStringExtra("uid");
//            String pwd = data.getStringExtra("upwd");
//            tvid.setText("账户名: "+id);
//            tvname.setText("黄澄澄的紫根牡丹");//淘宝默认昵称
//        }
//
//    }
}