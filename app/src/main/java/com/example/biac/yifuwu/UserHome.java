package com.example.biac.yifuwu;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import Pojo.OrdersOverviewInfo;
import Pojo.UserInfo;
import Utils.Common;
import Utils.NetUtils;

public class UserHome extends Activity {

    private UserInfo userInfo;

    public int new_orders;
    public int completed_orders;
    public int processing_orders;

    private int userId;
    private String workStation;

    private View workOrderBtn;
    private Button netsearchBtn, signalBtn, settingsBtn;

    private TextView nameText, jobText, giftText, rankingText, doneWorkNumbersText, daysOfLoginText, dateOflastLoginText;

    private TextView billBordTextView;

    private BadgeView badgeView;

    public String weatherInfoString;

    public LocationClient mLocationClient = null;
    public BDLocation mLocation;
    public BDLocationListener myListener = new MyLocationListener();
    public Application app;

    private static final int WEATHERINFORMATION = 1;
    private static final int ORDERSOVERVIEW = 2;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(android.os.Message msg){

            String weatherInfoUI = (String)msg.obj;

            setBillBordText(weatherInfoUI);
        }

    };

    private Handler handlerOverviewOrders = new Handler(){

        @Override
        public void handleMessage(Message msg){

            OrdersOverviewInfo overviewInfo = (OrdersOverviewInfo)msg.obj;

            new_orders = overviewInfo.getData().getNew_work_orders();
            completed_orders = overviewInfo.getData().getCompleted_work_orders();
            processing_orders = overviewInfo.getData().getProcessing_work_orders();

            if(overviewInfo.getData().getProcessing_work_orders() > 0){
                badgeView.setBadgeBackgroundColor(Color.GREEN);
                badgeView.setText(overviewInfo.getData().getProcessing_work_orders() + "");
                badgeView.show();
            }else if(overviewInfo.getData().getNew_work_orders() > 0){
                badgeView.setBadgeBackgroundColor(Color.RED);
                badgeView.setText(overviewInfo.getData().getNew_work_orders() + "");
                badgeView.show();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_home);

        userInfo = (UserInfo)getIntent().getParcelableExtra("userInfo");

        userId = userInfo.getData().getUser_id();
        workStation = userInfo.getData().getWork_station();

        getOrdersOverviewInfo(userId, workStation, "url");

        Log.i("Jin_new", new_orders + "");
        Log.i("Jin_completed", completed_orders + "");
        Log.i("Jin_processing", processing_orders + "");

        initView();

        //点击工单作业按钮，触发事件，查看工单列表
        workOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, WorkOrder.class);
                startActivity(intent);
            }
        });

        //点击网络查询按钮，触发的事件
        netsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //点击信号测试按钮，触发的事件
        signalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //点击设置按钮，触发的事件
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*
        fanlei 3.17
         */
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Common.LOCATION_ACTION);
//
//        this.registerReceiver(new LocationBroadcastReceiver(), filter);
//
//        //开启服务查询经纬度
//        Intent intent = new Intent();
//        intent.setClass(this, LocationService.class);
//        startService(intent);

        app = this.getApplication();

        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationClient.registerLocationListener(myListener);

        initLocation();

        mLocationClient.start();

        if(mLocationClient.isStarted())
            mLocationClient.requestLocation();
//
//
    }


    //初始化界面
    public void initView(){

        nameText = (TextView) findViewById(R.id.name);
        jobText = (TextView) findViewById(R.id.job);
        giftText = (TextView) findViewById(R.id.gift);
        rankingText = (TextView) findViewById(R.id.ranking);
        doneWorkNumbersText = (TextView) findViewById(R.id.doneWorkNumbers);
        daysOfLoginText = (TextView) findViewById(R.id.daysOfLogin);
        dateOflastLoginText = (TextView) findViewById(R.id.dateOflastLogin);

        workOrderBtn = findViewById(R.id.button1);
        netsearchBtn = (Button)findViewById(R.id.button2);
        signalBtn = (Button)findViewById(R.id.button3);
        settingsBtn = (Button)findViewById(R.id.button4);

        billBordTextView = (TextView)findViewById(R.id.billBoardText);

        badgeView = new BadgeView(this, workOrderBtn);

        badgeView.setTextSize(12);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);

//        setBadgeView();
        setNameText();
        setJobText();
        setGiftText();
        setRankingText();
        setDoneWorkNumbersText();
        setDaysOfLoginText();
        setDateOflastLoginText();
        //setBillBordText(weatherInformation);

    }

    //显示姓名
    public void setNameText(){
        nameText.setText("名称：" + userInfo.getData().getUser_id());
    }

    //显示工作
    public void setJobText(){
        jobText.setText("工位：" + userInfo.getData().getWork_station());
    }

    //显示积分
    public void setGiftText()
    {
        giftText.setText("积分：" + userInfo.getData().getPoints());
    }

    //显示排名
    public void setRankingText(){
        rankingText.setText("排名：" + userInfo.getData().getUser_rank());
    }

    //显示累计处理工单
    public void setDoneWorkNumbersText(){
        doneWorkNumbersText.setText("累计处理工单：" + userInfo.getData().getTotal_work_orders());
    }

    //显示累计登录天数
    public void setDaysOfLoginText(){
        daysOfLoginText.setText("累计登录天数：" + userInfo.getData().getTotal_login_days());
    }

    //显示上次登录时间
    public void setDateOflastLoginText(){
        dateOflastLoginText.setText("上次登录日期："+ userInfo.getData().getLast_login_time());
    }

    //设置工单作业按钮上的圆圈
//    public void setBadgeView(){
//
//        badgeView.setTextSize(12);                                  //设置大小
//        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        badgeView.setBadgeBackgroundColor(Color.BLUE);
////        badgeView.setText("10");                                    //设置未处理工单的数目
//        badgeView.show();
//
//    }

    //设置公告牌的内容
    public void setBillBordText(String billBordText){
        billBordTextView.setText(billBordText);

    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());

            location.getLocationDescribe();
            if(location.getCity()!=null){
                mLocation = location;
                mLocationClient.stop();
                String city = location.getCity();
                String country = mLocation.getCountry();

                Log.i("fl", city+country);
                Log.i("fl", mLocation.getLocType()+""+mLocation.getCityCode());


                //获取天气数据并解析
                //这里要改成城市列表录入
                String address = "http://apis.baidu.com/heweather/weather/free?city="+Common.getCityName(mLocation.getCity());
                NetUtils.getWeather(address, app, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                        Log.i("fl", "失败");

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        StringBuffer sb = new StringBuffer();
                        String line;
                        BufferedReader br = new BufferedReader(response.body().charStream());
                        while ((line = br.readLine()) != null)
                            sb.append(line);
                        br.close();

                        Log.i("fl天气", sb.toString());
                        //解析天气json
                        //WeatherInfo weatherInfo = Common.getWeather(sb.toString());
                        //Log.i("fl", weatherInfo.toString());
                        weatherInfoString = Common.getWeather(sb.toString());

                        Message message = Message.obtain();
                        message.obj = weatherInfoString;
                        message.what = WEATHERINFORMATION;
                        handler.sendMessage(message);

                    }
                });

            }else{
               mLocation = mLocationClient.getLastKnownLocation();

//                mLocationClient.stop();

            }

//            if(mLocation == null){
//                Toast.makeText(app, "定位失败", Toast.LENGTH_LONG).show();
//                return;
//            }

        }

    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public void getOrdersOverviewInfo(int user_id, String work_station, String url)
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

                    Message message = Message.obtain();
                    message.obj = overviewInfo;
                    message.what = ORDERSOVERVIEW;
                    handlerOverviewOrders.sendMessage(message);

                    Log.i("Jin123", " " + overviewInfo.getData().getCompleted_work_orders());

                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("user_id", user_id + "").add("work_station", work_station + "").build();

        try{

            NetUtils.postJson("http://115.156.245.150:8080/AndroidApp/getOrdersOverviewInfo", body, this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(UserHome.this, "获取工单概述失败", Toast.LENGTH_LONG).show();

        }

    }

}
