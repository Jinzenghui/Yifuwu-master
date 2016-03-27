package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Pojo.AcceptOrderMessage;
import Pojo.NewOrderDetailInfo;
import Utils.NetUtils;

public class Preprocessingdetailmessage extends Activity {

    private Button receiptBtn, releaseBtn, backBtn;

    private NewOrderDetailInfo newOrderDetailInfo;

    private String work_order_id;

    private TextView orderNumberTextView, orderGradeTextView, beginTimeTextView, typeTextView, areaTextView, usertelephonenumberTextView, symptomTextView, complaintstimeTextView, complaintsaddressTextView;
    private TextView guestbookTextView, preprocessingTextView;

    private TextView guestBookTextView, preProcessingMessageTextView;

    int minute;
    int second;
    int hour;

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
        setContentView(R.layout.activity_preprocessingdetailmessage);

        newOrderDetailInfo = (NewOrderDetailInfo)getIntent().getParcelableExtra("NewOrderDetailInfo");
        work_order_id = newOrderDetailInfo.getData().getWork_order_id();

        String remaining_time =  "0000-00-00 " + newOrderDetailInfo.getData().getRemaining_time();
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            date = formatter.parse(remaining_time);
        }catch (Exception e){
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        hour = cal.get(Calendar.DATE) * 24 + cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);


        receiptBtn = (Button)findViewById(R.id.btnReceipt);
        releaseBtn = (Button)findViewById(R.id.btnRelease);
        backBtn = (Button)findViewById(R.id.btnBack);

        receiptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Preprocessingdetailmessage.this, acknowledgement.class);
                intent.putExtra("Work_Order_Id", work_order_id);
                startActivity(intent);
            }
        });

        releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseOrder(work_order_id);
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
        beginTimeTextView.setText("发起时间   " + newOrderDetailInfo.getData().getCreate_time());
    }

    //显示投诉类型
    public void setTypeTextView(){
        typeTextView.setText("类        型   " + newOrderDetailInfo.getData().getWork_order_type_code() );
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
        symptomTextView.setText("故障现象    " + newOrderDetailInfo.getData().getWork_order_type_code());
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

    public void releaseOrder(String work_order_id){

        final Gson gson = new Gson();

        Callback callback = new Callback(){

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                AcceptOrderMessage acceptOrderMessage;

                if(response.isSuccessful()){

                    acceptOrderMessage = gson.fromJson(response.body().charStream(), AcceptOrderMessage.class);


                    if(acceptOrderMessage.getResult() == 0){

                        Toast.makeText(Preprocessingdetailmessage.this, "释放成功", Toast.LENGTH_SHORT).show();

                    }else if(acceptOrderMessage.getResult() == -1){

                        Toast.makeText(Preprocessingdetailmessage.this, "释放失败" + acceptOrderMessage.getText(), Toast.LENGTH_LONG).show();

                    }

                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("work_order_id", work_order_id + "").add("handle_action", 3 + "").build();

        try{

            NetUtils.postJson("http://120.27.106.26/app/changeOrderStatus", body, this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(Preprocessingdetailmessage.this, "接单失败", Toast.LENGTH_LONG).show();

        }
    }

}
