package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import Pojo.OrdersOverviewInfo;
import Pojo.UserInfo;
import Utils.NetUtils;

public class login extends Activity {

    private ProgressBar progressBar;
    private Button backButton;
    private UserInfo user_Info;

    private int userId;
    private String workStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        progressBar = (ProgressBar)findViewById(R.id.pgBar);
        backButton = (Button)findViewById(R.id.btn_back);

        user_Info = (UserInfo)getIntent().getParcelableExtra("userInfo");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    getOrdersOverviewInfo();
                    finish();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void getOrdersOverviewInfo()
    {

        final Gson gson = new Gson();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                OrdersOverviewInfo overviewInfo;

                if(response.isSuccessful()){

                    overviewInfo = gson.fromJson(response.body().charStream(), OrdersOverviewInfo.class);

                    Log.i("Jin123", " " + overviewInfo.getData().getCompleted_work_orders());

                    Intent intent = new Intent(login.this, UserHome.class);
                    intent.putExtra("OrdersOverviewInfo", overviewInfo);
                    intent.putExtra("UserInfo", user_Info);
                    Log.i("Jin-login", user_Info.getData().getWork_station()+"");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        try {

            NetUtils.getJson("http://120.27.106.26/app/getOrdersOverviewInfo", this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(login.this, "获取工单概述失败", Toast.LENGTH_LONG).show();

        }

    }

}
