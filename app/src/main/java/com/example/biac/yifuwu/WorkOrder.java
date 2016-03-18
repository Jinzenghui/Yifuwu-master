package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class WorkOrder extends Activity {

    private Button newworkorderBtn, orderprocessingBtn, orderdoneBtn, backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_work_order);

        initView();



        newworkorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkOrder.this, PendingOrder.class);
                startActivity(intent);
            }
        });

        orderprocessingBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        orderdoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        newworkorderBtn.setText("新工单：6件");
    }

    //设置处理中按钮上的文字
    public void setOrderProcessingBtnText(){
        orderprocessingBtn.setText("处理中：2件");
    }

}
