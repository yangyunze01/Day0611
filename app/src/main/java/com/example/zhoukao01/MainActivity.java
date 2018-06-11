package com.example.zhoukao01;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhoukao01.http.HttpConfig;
import com.example.zhoukao01.http.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   private TextView tv;
       private TextView text1,text2,text3,text4,text5,text6,text7,text8;
 private List<AdBean.DataBean> list=new ArrayList<>();
 private AdBean adBean;
 private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        datas();
    }


    private void initView() {
          tv=findViewById(R.id.tv_title);
        text1=  findViewById(R.id.text1);
        text1.setTextColor(Color.YELLOW);
        text2=  findViewById(R.id.text2);
        text2.setTextColor(Color.GREEN);
        text3=  findViewById(R.id.text3);
        text3.setTextColor(Color.RED);
        text4=  findViewById(R.id.text4);
        text4.setTextColor(Color.BLUE);
        text5=  findViewById(R.id.text5);
        text5.setTextColor(Color.BLUE);
        text6=  findViewById(R.id.text6);
        text6.setTextColor(Color.YELLOW);
        text7=  findViewById(R.id.text7);
        text7.setTextColor(Color.CYAN);
        text8=  findViewById(R.id.text8);
        text8.setTextColor(Color.LTGRAY);
















    }
    private void datas() {
        HttpUtils httpUtils = HttpUtils.getInstance();
        httpUtils.get(HttpConfig.SHUJU);

        httpUtils.setHttpUtilsListener(new HttpUtils.HttpUtilListener() {
            @Override
            public void getSuccess(String json) {
                Gson gson = new Gson();
                 adBean = gson.fromJson(json, AdBean.class);
                 adBean.getData();
            }

            @Override
            public void getError(String error) {

            }
        });
}


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
          }
}
