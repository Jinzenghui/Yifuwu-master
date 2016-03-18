package com.example.biac.yifuwu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class OrderDetail extends Activity {

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

        timeView = (TextView)findViewById(R.id.resttime);

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
}
