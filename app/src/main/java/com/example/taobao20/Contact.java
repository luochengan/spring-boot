package com.example.taobao20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Contact extends AppCompatActivity {
    // 适配器
    private RecyclerContactsAdapter adapter;
    // RecyclerView
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        ImageButton img_btn= findViewById(R.id.button_back);
        img_btn.setOnClickListener(v -> finish());
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, 0x123);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == 0){
            if (requestCode == 0x123) {
                // 定义两个List来封装系统的联系人信息、指定联系人的电话号码、Email等详情
                List<String> names = new ArrayList<>();
                List<List<String>> details = new ArrayList<>();
                // 使用ContentResolver查找联系人数据
                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                // 遍历查询结果，获取系统中所有联系人
                while (cursor.moveToNext()) {
                    // 获取联系人ID
                    @SuppressLint("Range")
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    // 获取联系人的名字
                    @SuppressLint("Range")
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    names.add(name);
                    // 使用ContentResolver查找联系人的电话号码
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null, null);
                    List<String> detail = new ArrayList<>();
                    // 遍历查询结果，获取该联系人的多个电话号码
                    while (phones.moveToNext()) {
                        // 获取查询结果中电话号码列中数据
                        @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        detail.add("电话：" + phoneNumber);
                    }
                    phones.close();
                    details.add(detail);
                }
                cursor.close();
                //        recycler
                recyclerView = (RecyclerView) findViewById(R.id.rv_contacts);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter = new RecyclerContactsAdapter(names,details);
                recyclerView.setAdapter(adapter);
            }
        }else
        {
            Toast.makeText(this, "访问失败", Toast.LENGTH_SHORT).show();
        }
    }

}