package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Pojo.NewOdersOverviewInfo;
import Pojo.NewOrderDetailInfo;
import Utils.NetUtils;

public class PendingOrder extends Activity {

    private static final int NEWORDERSOVERVIEWINFO = 1;

    private String work_order_status;

    private NewOdersOverviewInfo newOrdersOverviewInfo;
    private String work_order_id;

    private List<Map<String, String>> mData;

    private ListView lv;
    private Button btnLeft, btnRight,btnback;
    private TextView tv1, tv2;

    private int totalnumber;     //待处理工单的总数
    private int pagenumber;         //页数

    int VIEW_COUNT = 3;
    int index = 0;      //页数的索引

    View.OnClickListener cl;

    MyAdapter ma;

    public final class ViewHolder{

        public TextView number;
        public TextView time;
        public TextView type;
        public TextView address;

    }

    private List<Map<String, String>>getDate(NewOdersOverviewInfo newOrdersOverviewInfo ){

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();

        for(int i=0; i < newOrdersOverviewInfo.getSum(); i++){

            map.put("number", "编号：" + newOrdersOverviewInfo.getData().get(i).getWork_order_id());

            map.put("time", "时间：" + newOrdersOverviewInfo.getData().get(i).getCreate_time());

            map.put("type", "类型：" + newOrdersOverviewInfo.getData().get(i).getWork_order_type());

            map.put("address", "地区：" + newOrdersOverviewInfo.getData().get(i).getArea());

            list.add(map);

        }

        return list;
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(android.os.Message msg){

            newOrdersOverviewInfo = (NewOdersOverviewInfo) msg.obj;

            mData = getDate(newOrdersOverviewInfo);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pending_order);

        newOrdersOverviewInfo = (NewOdersOverviewInfo)getIntent().getParcelableExtra("FirstPageNewOrderOverviewInfo");

        totalnumber = (int)getIntent().getIntExtra("New_Orders_Number", 0);

        work_order_status = (String)getIntent().getStringExtra("Work_order_status");

        mData = getDate(newOrdersOverviewInfo);

        initView();
        ma = new MyAdapter(this);

        lv.setAdapter(ma);

        cl = new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.btnLeft:
                        leftView();
                        break;

                    case R.id.btnRight:
                        rightView();
                        break;

                    case R.id.workorderback:
                        finish();
                        break;
                }
            }
        };

        btnLeft.setOnClickListener(cl);
        btnRight.setOnClickListener(cl);
        btnback.setOnClickListener(cl);
        checkButton();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                work_order_id = newOrdersOverviewInfo.getData().get(position).getWork_order_id();

                Log.i("Jin12345", work_order_id);

                getNewOrderDetailInfo(work_order_status, work_order_id);

                Log.i("test", "this is a test!" + position + index);

            }
        });

    }

    public void initView(){

        lv = (ListView)findViewById(R.id.listview);

        lv.setDividerHeight(20);

        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnRight = (Button)findViewById(R.id.btnRight);
        btnback = (Button)findViewById(R.id.workorderback);
        tv1 = (TextView)findViewById(R.id.totalpagenumbers);
        tv2 = (TextView)findViewById(R.id.pagenumber);

        setTotalnumber();
        setPagenumber();
    }

    public void leftView(){

        index--;

        getNewOrdersOverviewInfo(work_order_status,index);

        ma.notifyDataSetChanged();

        checkButton();

        setPagenumber();
    }

    public void rightView(){

        index++;

        getNewOrdersOverviewInfo(work_order_status, index);

        ma.notifyDataSetChanged();

        checkButton();

        setPagenumber();
    }


    public void checkButton(){

//        int totalnumber = getTotalnumber();

        double pagenumber = Math.ceil((double)totalnumber / VIEW_COUNT);

        int pn = (int) pagenumber;

        if(pn == 1){
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
        }else{
            if(index <= 0){
                btnLeft.setEnabled(false);
                btnRight.setEnabled(true);
            }else if((index + 1) == pn){
                btnRight.setEnabled(false);
                btnLeft.setEnabled(true);
            }else {
                btnLeft.setEnabled(true);
                btnRight.setEnabled(true);
            }

        }

    }

    //显示总的工单数目
    public void setTotalnumber(){

//        int totalnumber = getTotalnumber();

        tv1.setText("总数：" + totalnumber);
    }

    //显示页数
    public void setPagenumber(){

//        int totalnumber = getTotalnumber();

        double pagenumber = Math.ceil((double) totalnumber / VIEW_COUNT);

       int pn = (int) pagenumber;

        tv2.setText("页数：" + (index + 1) + "/" + pn );
    }

    //获得工单的总数
//    public int getTotalnumber(){
//
//        return mData.size();
//
//    }

    //获得页数的索引
    public int getPageIndex(){

        return index;
    }


    //ListView的适配器
    public class MyAdapter extends BaseAdapter{

        private LayoutInflater mInflater;

        Activity activity;

        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {

//            int ori = VIEW_COUNT * index;
//
//            if(mData.size() - ori < VIEW_COUNT){
//                return mData.size() - ori;
//            }else{
//                return VIEW_COUNT;
//            }

            return mData.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){

                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.pendinglist, null);
                holder.number = (TextView)convertView.findViewById(R.id.number);
                holder.time = (TextView)convertView.findViewById(R.id.time);
                holder.type = (TextView)convertView.findViewById(R.id.type);
                holder.address = (TextView)convertView.findViewById(R.id.address);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.number.setText((String)mData.get(position).get("number"));
            holder.time.setText((String)mData.get(position).get("time"));
            holder.type.setText((String) mData.get(position).get("type"));
            holder.address.setText((String)mData.get(position).get("address"));

            return convertView;
        }
    }

    public void getNewOrderDetailInfo(final String work_order_status, String work_order_id)
    {
        final Gson gson = new Gson();

        Callback callback = new Callback(){

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                NewOrderDetailInfo newOrderDetailInfo;

                if(response.isSuccessful()){

                    newOrderDetailInfo = gson.fromJson(response.body().charStream(), NewOrderDetailInfo.class);

                    if(work_order_status == 1 + "") {

                        Intent intent = new Intent(PendingOrder.this, OrderDetail.class);
                        intent.putExtra("NewOrderDetailInfo", newOrderDetailInfo);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }else if(work_order_status == (2 + "")){
                        Intent intent = new Intent(PendingOrder.this, PreprocessingOrderDetail.class);
                        intent.putExtra("NewOrderDetailInfo", newOrderDetailInfo);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }else if(work_order_status == (3 + "")){

                        Intent intent = new Intent(PendingOrder.this, CompletedOrderDetail.class);
                        intent.putExtra("NewOrderDetailInfo", newOrderDetailInfo);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("work_order_id", work_order_id).build();

        try{

            NetUtils.postJson("http://120.27.106.26/app/getNewOrdersDetailsInfo", body, this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(PendingOrder.this, "获取工单概述失败", Toast.LENGTH_LONG).show();

        }

    }

    public void getNewOrdersOverviewInfo(String work_order_status  ,int pagenumber )
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

                    Message message = Message.obtain();
                    message.obj = newOdersOverviewInfo;
                    message.what = NEWORDERSOVERVIEWINFO;
                    handler.sendMessage(message);


                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("work_order_status", work_order_status).add("page", pagenumber + "").add("num", 3 + "").build();

        try{

            NetUtils.postJson("http://120.27.106.26/app/getNewOrdersOverviewInfo", body, this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(PendingOrder.this, "无新工单", Toast.LENGTH_LONG).show();

        }

    }





}
