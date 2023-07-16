package com.example.taobao20.dao;

import android.content.Context;

import com.example.taobao20.bean.HomeBottomiteminfo;
import com.example.taobao20.bean.ShopinfoForjson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParse {
    private static JsonParse instance;
    private JsonParse() {


    }
    public static JsonParse getInstance(){
        if (instance==null){
            instance=new JsonParse();
        }
        return instance;
    }

    private String read(InputStream in)  {
        BufferedReader reader =null;
        StringBuilder sb;
        String line;
        try {
            sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return "";
        }finally {
            try {
                if (in!=null) in.close();
                if (reader!=null) reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public List<HomeBottomiteminfo> getInfoFromJsonbottomitem(Context context) {
        List<HomeBottomiteminfo> infos =new ArrayList<>();
        InputStream is;
        try {
            is= context.getResources().getAssets().open("items.json");
            String json =read(is);
            Gson gson= new Gson();
            Type listType = new TypeToken<List<HomeBottomiteminfo>>(){
            }.getType();
            return gson.fromJson(json,listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infos;
    }

    public List<ShopinfoForjson> getInfoFromJsonshopinfo(Context context) {
        List<ShopinfoForjson> infos =new ArrayList<>();
        InputStream is;
        try {
            is= context.getResources().getAssets().open("shopinfo.json");
            String json =read(is);
            Gson gson= new Gson();
            Type listType = new TypeToken<List<ShopinfoForjson>>(){
            }.getType();
            return gson.fromJson(json,listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infos;
    }




}
