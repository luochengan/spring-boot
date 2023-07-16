package com.example.taobao20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Messagelogin extends AppCompatActivity {

    private String pnum=null;
    private String getted_num=null;
    private String getted_ma=null;
    private boolean flag=true;
    private TextView tvget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagelogin);
        //去除默认标题栏
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        initview();
    }
    private void initview() {
        Button btn2= findViewById(R.id.buttonback2);
        Button denglu2=findViewById(R.id.denglu2);
        tvget= findViewById(R.id.get_ma);
        EditText phonenum= findViewById(R.id.phonenumber);
        EditText yanzhengma= findViewById(R.id.yanzhengma);

        DealMessageReceiver msr= new DealMessageReceiver();
        String action="android.provider.Telephony.SMS_RECEIVED";
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction(action);
        registerReceiver(msr,intentFilter);//注册消息接收广播

        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        String action2="com.example.taobao20";
        IntentFilter intentFilter2= new IntentFilter();
        intentFilter2.addAction(action2);
        registerReceiver(receiver,intentFilter2);//注册时间广播

        btn2.setOnClickListener(v -> finish());

        tvget.setOnClickListener(v -> {
             pnum=phonenum.getText().toString();
            if (pnum.equals("")){
                Toast.makeText(Messagelogin.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
            }else{
                Log.i("Msg", "initview:"+pnum);
                tvget.setEnabled(true);
                tvget.setTextColor(Color.parseColor("#F28500"));//点击获取验证码后会变颜色
                if (flag){
                    Log.i("flag", "initview: flag=true");
                    Intent intent= new Intent(Messagelogin.this,TimeService.class);
                    startService(intent);
                    tvget.setText("重新发送60s");
                }

                if (pnum.equals(getted_num)){
                    yanzhengma.setText(getted_ma);
                    Toast.makeText(this, "号码"+pnum+"对应的短信验证码自动填入成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "没有号码"+pnum+"对应的短信验证码！", Toast.LENGTH_SHORT).show();
                    yanzhengma.setText("");
                }
            }
        });

        denglu2.setOnClickListener(v -> {
            String ma= yanzhengma.getText().toString();
            if (ma.equals("")) {
                Toast.makeText(Messagelogin.this,"请输入验证码",Toast.LENGTH_LONG).show();
            }else{
                //登录时验证与手机号匹配的验证码
                String pnum=phonenum.getText().toString();
                if (pnum.equals(getted_num)&&ma.equals(getted_ma)){
                    Intent intent = new Intent(Messagelogin.this,MainActivity.class);
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);//登录成功返回首页
                }else{
                    Toast.makeText(this, "验证码与手机号不匹配", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int time = intent.getIntExtra("time",60);
            if(time < 60 && time >0){
                flag = false;
                tvget.setText("重新发送"+time+"秒");
                Log.i("Msgtime","剩余"+time);
            }else{
                flag = true;
                tvget.setTextColor(Color.parseColor("#2295FA"));
                tvget.setText("再次获取");
            }
        }
    }

    public class DealMessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取所有的短信数据
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            for (Object obj : objs) {
                // 将Pdu中的对象转化成SmsMessage对象
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                String body = smsMessage.getMessageBody();
                String sender = smsMessage.getOriginatingAddress();
                if (body.contains("验证码")&&sender.equals(pnum)){
                    getted_ma=findNum(body);
                    getted_num=sender;
                    Log.i("Msg0", "gettma:" + getted_ma);
                    Log.i("Msg0", "sender:" + sender);
                }else{
                    Log.i("Msg","onReceive: 无验证码");
                }
            }
        }
    }
    public String findNum(String str1){//从字符串信息中筛选出数字
        StringBuilder str2= new StringBuilder();
        str1=str1.trim();
        if(!"".equals(str1)){
            for(int i=0;i<str1.length();i++){
                if(str1.charAt(i)>47 && str1.charAt(i)<58){
                    str2.append(str1.charAt(i));
                }
            }
        }
        return str2.toString();
    }
}