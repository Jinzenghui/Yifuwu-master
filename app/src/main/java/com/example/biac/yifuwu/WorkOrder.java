package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import Pojo.NewOdersOverviewInfo;
import Pojo.OrdersOverviewInfo;
import Pojo.UserInfo;
import Utils.NetUtils;

public class WorkOrder extends Activity {

    private UserInfo userInfo;
    private int work_order_status = 1;

    private int userId;

    private OrdersOverviewInfo overviewInfo;

    private Button newworkorderBtn, orderprocessingBtn, orderdoneBtn, backBtn;

    public int new_orders;
    public int processing_orders;
    public int completed_work_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_work_order);

        overviewInfo = (OrdersOverviewInfo)getIntent().getParcelableExtra("OrdersOverviewInfo");

        new_orders = overviewInfo.getData().getNew_work_orders();
        processing_orders = overviewInfo.getData().getProcessing_work_orders();
        completed_work_orders = overviewInfo.getData().getCompleted_work_orders();

        userId =  getIntent().getIntExtra("UserId",0);
        Log.i("Jin-workorder", userId +"");

        initView();

        newworkorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new_orders == 0){
                    Toast.makeText(WorkOrder.this, "没有新工单", Toast.LENGTH_LONG).show();
                }else {

                    getNewOrdersOverviewInfo(1+"");
                }

            }
        });

        orderprocessingBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(processing_orders == 0)
                {
                    Toast.makeText(WorkOrder.this, "没有正在处理的工单", Toast.LENGTH_LONG).show();
                }else{
                    getNewOrdersOverviewInfo(2+"");
                }

            }
        });

        orderdoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewOrdersOverviewInfo(3+"");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initView(){

        newworkorderBtn = (Button)findViewById(R.id.newworkorder);
        orderprocessingBtn = (Button)findViewById(R.id.orderprocessing);
        orderdoneBtn = (Button)findViewById(R.id.orderdone);
        backBtn = (Button)findViewById(R.id.back);

        setNewworkorderBtnText();
        setOrderProcessingBtnText();

    }

    //设置新工单按钮上的文字
    public void setNewworkorderBtnText(){
        newworkorderBtn.setText("新工单：" +  new_orders + "件");
    }

    //设置处理中按钮上的文字
    public void setOrderProcessingBtnText(){
        orderprocessingBtn.setText("处理中：" + processing_orders + "件");
    }

    public void getNewOrdersOverviewInfo(final String work_order_status)
    {
        final Gson gson = new Gson();

        Callback callback = new Callback(){

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                NewOdersOverviewInfo newOdersOverviewInfo;

                if(response.isSuccessful()){

                    newOdersOverviewInfo = gson.fromJson(response.body().charStream(), NewOdersOverviewInfo.class);

                    Intent intent = new Intent(WorkOrder.this, PendingOrder.class);
                    intent.putExtra("FirstPageNewOrderOverviewInfo", newOdersOverviewInfo);
                    intent.putExtra("New_Orders_Number", new_orders);
                    intent.putExtra("Work_order_status", work_order_status);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("work_order_status", work_order_status).add("page", 0 + "").add("num", 3 + "").build();

        try{

            NetUtils.postJson("http://120.27.106.26/app/getNewOrdersOverviewInfo", body, this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(WorkOrder.this, "获取工单概述失败", Toast.LENGTH_LONG).show();

        }

    }

}
