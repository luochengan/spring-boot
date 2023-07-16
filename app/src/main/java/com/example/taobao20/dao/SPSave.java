package com.example.taobao20.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class SPSave {
    public static boolean saveUserInfo(Context context, String account, String password){
        SharedPreferences sp = context.getSharedPreferences("logindata",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username",account);
        edit.putString("pwd",password);
        edit.apply();
        return true;
    }
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp =context.getSharedPreferences("logindata",Context.MODE_PRIVATE);
        if(sp.getString("username",null)==null) return null;//判断sp是否添加过值
        Log.i("sp", "getUserInfo: "+sp);
        String account= sp.getString("username",null);
        String password = sp.getString("pwd",null);
        Map<String,String> userMap =new HashMap<String, String>();
        userMap.put("account",account);
        userMap.put("password",password);
        return userMap;
    }

}
