package com.example.biac.yifuwu;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class OrderDetail extends Activity {

    private TextView orderNumberTextView, orderGradeTextView, beginTimeTextView, typeTextView, areaTextView, usertelephonenumberTextView, symptomTextView, complaintstimeTextView, complaintsaddressTextView;
    private TextView guestbookTextView, preprocessingTextView;

    private Button acceptBtn, giveupBtn;

    int minute = 48;
    int second = 0;
    int hour = 37;

    private TextView timeView;
    private Timer timer;
    private TimerTask timerTask;

    Handler handler = new Handler(){

        public void handleMessage(Message msg){
            if(hour == 0){
                if(minute == 0){
                    if(second == 0){
                        timeView.setText("已超时！");
                        if(timer != null){
                            timer.cancel();
                            timer = null;
                        }

                        if(timerTask != null) {
                            timerTask = null;
                        }
                    }else{
                        second--;
                        if(second >= 10){
                            timeView.setText("0" + hour + ":" + "0" + minute + ":" + second);
                        }else{
                            timeView.setText("0" + hour + ":" + "0" + minute + ":0" + second );
                        }
                    }
                }else{
                    if(second == 0){
                        second = 59;
                        minute--;
                        if(minute >= 10){
                            timeView.setText("0" + hour + ":" + minute + ":" + second);
                        }else{
                            timeView.setText("0" + hour + ":" + "0" + minute + ":" + second);
                        }
                    }else {
                        second--;
                        if(second >= 10){
                            if(minute >= 10){
                                timeView.setText("0" + hour + ":" + minute + ":0" + second);
                            }else{
                                timeView.setText("0" + hour + ":0" + minute + ":0" + second);
                            }
                        }
                    }
                }

            }else {
                if(minute == 0){
                    if(second == 0){
                        second = 59;
                        minute = 59;
                        hour--;
                        if(hour >= 10){
                            timeView.setText(hour + ":0" + minute + ":" + second);
                        }else{
                            timeView.setText("0" + hour + ":0" + minute + ":0" + second);
                        }
                    }else{
                        second--;
                        if(hour >= 10)
                        {
                            if(second >= 10){
                                timeView.setText(hour + ":0" + minute + ":" + second);
                            }else{
                                timeView.setText(hour + ":0" + minute + ":0" + second);
                            }
                        }
                    }
                }else{
                    if(second == 0){
                        second = 59;
                        minute--;
                        if(hour >= 10){
                            if(minute >= 10){
                                timeView.setText(hour + ":" + minute + ":" + second);
                            }else{
                                timeView.setText(hour + ":0" + minute + ":" + second);
                            }
                        }else{
                            if(minute >= 10){
                                timeView.setText("0" + hour + ":" + minute + ":" + second);
                            }else{
                                timeView.setText("0" + hour + ":0" + minute + ":" + second);
                            }
                        }
                    }else{
                        second--;
                        if(hour >= 10){
                            if(minute >= 10){
                                if(second >= 10){
                                    timeView.setText(hour + ":" + minute + ":" + second);
                                }else {
                                    timeView.setText(hour + ":" + minute + ":0" + second);
                                }
                            }else{
                                if(second >= 10){
                                    timeView.setText(hour + ":0" + minute + ":" + second);
                                }else {
                                    timeView.setText(hour + ":0" + minute + ":0" + second);
                                }
                            }
                        }else{
                            if(minute >= 10){
                                if(second >= 10){
                                    timeView.setText("0" + hour + ":" + minute + ":" + second);
                                }else {
                                    timeView.setText("0" + hour + ":" + minute + ":0" + second);
                                }
                            }else{
                                if(second >= 10){
                                    timeView.setText("0" + hour + ":0" + minute + ":" + second);
                                }else {
                                    timeView.setText("0" + hour + ":0" + minute + ":0" + second);
                                }
                            }
                        }
                    }

                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);

        initView();

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        giveupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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

        acceptBtn = (Button)findViewById(R.id.accept);
        giveupBtn = (Button)findViewById(R.id.giveup);

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
        orderNumberTextView.setText("工单编号   16030854321");
    }

    //显示工单等级
    public void setOrderGradeTextView(){
        orderGradeTextView.setText("工单等级    一般");
    }

    //显示剩余时间
    public void settimeViewTextView(){
        timeView.setText(hour + ":" + minute + ":" + second);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


    //显示发起时间
    public void setBeginTimeTextView(){
        beginTimeTextView.setText("发起时间   2016/3/8 12:28");
    }

    //显示投诉类型
    public void setTypeTextView(){
        typeTextView.setText("类        型   2G语音投诉");
    }

    //显示地区
    public void setAreaTextView(){
        areaTextView.setText("地        区   宜昌市兴山县");
    }

    //显示用户号码
    public void setUsertelephonenumberTextView(){
        usertelephonenumberTextView.setText("用户号码    18907182187");
    }

    //显示故障现象
    public void setSymptomTextView(){
        symptomTextView.setText("故障现象    2G语音主叫失败");
    }

    //显示投诉时间
    public void setComplaintstimeTextView(){
        complaintstimeTextView.setText("投诉时间    2016/3/8  11:47");
    }

    //显示投诉地点
    public void setComplaintsaddressTextView(){
        complaintsaddressTextView.setText("宜昌市兴山县昭君镇米仓渡口");
        complaintsaddressTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    //显示用户留言
    public void setGuestbookTextView(){
        guestbookTextView.setText("上得了网，打不了电话，快点解决！！！");
    }

    //显示预处理信息
    public void setPreprocessingTextView(){
        preprocessingTextView.setText("关联基站/小区有故障");
    }
}
