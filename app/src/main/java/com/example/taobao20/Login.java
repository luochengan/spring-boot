package com.example.taobao20;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taobao20.dao.SPSave;

public class Login extends AppCompatActivity {
    private EditText tvuid;
    private EditText tvpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //去除默认标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        initview();
    }

    private void initview() {
        tvuid = findViewById(R.id.et_account);
        tvpwd = findViewById(R.id.et_password);
        TextView tvmsg= findViewById(R.id.tV_tomessage);
        TextView tvforgotpwd=findViewById(R.id.forgotpwd);
        Button back = findViewById(R.id.buttonfh);

        back.setOnClickListener(view -> finish());

        tvmsg.setOnClickListener(v -> {
            Intent intent =new Intent(Login.this,Messagelogin.class);
            startActivity(intent);
        });
        tvforgotpwd.setOnClickListener(v -> {
            Intent intent =new Intent(Login.this,Messagelogin.class);
            startActivity(intent);
        });

        TextView login = findViewById(R.id.denglu);
        //弃用登录
//        login.setOnClickListener(view -> {
//            String uid= tvuid.getText().toString();
//            String upwd =tvpwd.getText().toString();
//
//            if (uid.equals("")){
//                Toast.makeText(this, "请输入账户名", Toast.LENGTH_LONG).show();
////                Log.i("Login", "0"+uid);
//            }else {
////                Log.i("Login", "1");
//                if (upwd.equals("")){
//                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
//                }else {
//                    Intent intent = new Intent();
//                    intent.putExtra("uid", uid);
//                    intent.putExtra("upwd", upwd);
//                    setResult(2, intent);
//                    finish();
//                }
//            }
//        });

        // 存入shared preferences登录
        login.setOnClickListener(v -> {
            String account = tvuid.getText().toString();
            String password =tvpwd.getText().toString();
            if (TextUtils.isEmpty(account)){
                Toast.makeText(Login.this,"请输入QQ账号",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Login.this,"请输入密码",Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
            boolean isSaveSuccess= SPSave.saveUserInfo(Login.this,account,password);
            if (isSaveSuccess){
                Toast.makeText(Login.this,"保存成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Login.this,"保存失败",Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);




        });





    }



}