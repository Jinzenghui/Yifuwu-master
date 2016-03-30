package com.example.biac.yifuwu;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import Pojo.NewOrderDetailInfo;

public class CompletedOrderDetail extends Activity {

    private TextView orderNumberTextView, orderGradeTextView, beginTimeTextView, typeTextView, areaTextView, usertelephonenumberTextView, symptomTextView, complaintstimeTextView, complaintsaddressTextView;
    private TextView guestbookTextView, preprocessingTextView;

    private int user_id;

    private NewOrderDetailInfo newOrderDetailInfo;

    private TextView guestBookTextView, preProcessingMessageTextView;

    private String work_order_id;

    private TextView timeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_completed_order_detail);
    }

    public void initView(){
        orderNumberTextView = (TextView)findViewById(R.id.ordernumber);
        orderGradeTextView = (TextView)findViewById(R.id.ordergrade);
        beginTimeTextView = (TextView)findViewById(R.id.begintime);
        typeTextView = (TextView)findViewById(R.id.type);
        areaTextView = (TextView)findViewById(R.id.area);
        usertelephonenumberTextView = (TextView)findViewById(R.id.usertelephonenumber);
        symptomTextView = (TextView)findViewById(R.id.symptom);
        complaintstimeTextView = (TextView)findViewById(R.id.complaintstime);
        complaintsaddressTextView = (TextView)findViewById(R.id.complaints_address);

        guestbookTextView = (TextView)findViewById(R.id.guestbook_message);
        preprocessingTextView = (TextView)findViewById(R.id.preprocessingmessage);

        timeView = (TextView)findViewById(R.id.resttime);

        guestBookTextView = (TextView)findViewById(R.id.guestbook_message);

        preProcessingMessageTextView = (TextView)findViewById(R.id.preprocessingmessage);

        setOrderNumberTextView();
        setOrderGradeTextView();
        setTypeTextView();
        setBeginTimeTextView();
        setAreaTextView();
        setUsertelephonenumberTextView();
        setSymptomTextView();
        setComplaintstimeTextView();
        setComplaintsaddressTextView();
        settimeViewTextView();
        setGuestbookTextView();
        setPreprocessingTextView();

    }


    //显示工单编号
    public void setOrderNumberTextView(){
        orderNumberTextView.setText("工单编号   " + newOrderDetailInfo.getData().getWork_order_id());
    }

    //显示工单等级
    public void setOrderGradeTextView(){
        orderGradeTextView.setText("工单等级    " + newOrderDetailInfo.getData().getWork_order_level());
    }

    //显示剩余时间
    public void settimeViewTextView(){
//        timeView.setText(hour + ":" + minute + ":" + second);
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                Message msg = new Message();
//                msg.what = 0;
//                handler.sendMessage(msg);
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(timerTask, 0, 1000);
    }


    //显示发起时间
    public void setBeginTimeTextView(){
        beginTimeTextView.setText("发起时间   " + newOrderDetailInfo.getData().getCreate_time());
    }

    //显示投诉类型
    public void setTypeTextView(){
        typeTextView.setText("类        型   " + newOrderDetailInfo.getData().getWork_order_type() );
    }

    //显示地区
    public void setAreaTextView(){
        areaTextView.setText("地        区   " + newOrderDetailInfo.getData().getComplaint_position());
    }

    //显示用户号码
    public void setUsertelephonenumberTextView(){
        usertelephonenumberTextView.setText("用户号码    " + newOrderDetailInfo.getData().getComplaint_tele_num());
    }

    //显示故障现象
    public void setSymptomTextView(){
        symptomTextView.setText("故障现象    " + newOrderDetailInfo.getData().getWork_order_type());
    }

    //显示投诉时间
    public void setComplaintstimeTextView(){
        complaintstimeTextView.setText("投诉时间    " + newOrderDetailInfo.getData().getComplaint_time());
    }

    //显示投诉地点
    public void setComplaintsaddressTextView(){
        complaintsaddressTextView.setText(newOrderDetailInfo.getData().getComplaint_position());
        complaintsaddressTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    //显示用户留言
    public void setGuestbookTextView(){
        guestbookTextView.setText(newOrderDetailInfo.getData().getMessage());
    }

    //显示预处理信息
    public void setPreprocessingTextView(){
        preprocessingTextView.setText(newOrderDetailInfo.getData().getPre_deal_result());
    }



}
