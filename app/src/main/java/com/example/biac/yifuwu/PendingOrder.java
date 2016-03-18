package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingOrder extends Activity {

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

    private List<Map<String, String>>getDate(){

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("number", "编号：201603084332");
        map.put("time", "时间：2016-3-8 14:21");
        map.put("type", "类型：4G数据投诉");
        map.put("address", "地区：宜昌-长阳县");
        list.add(map);

        map = new HashMap<String, String>();
        map.put("number", "编号：201603084332");
        map.put("time", "时间：2016-3-8 10:01");
        map.put("type", "类型：3G数据投诉");
        map.put("address", "地区：宜昌-秭归县");
        list.add(map);

        map = new HashMap<String, String>();
        map.put("number", "编号：201603084332");
        map.put("time", "时间：2016-3-7 21:51");
        map.put("type", "类型：2G语音投诉");
        map.put("address", "地区：宜昌-城区");
        list.add(map);

        map = new HashMap<String, String>();
        map.put("number", "编号：201603084332");
        map.put("time", "时间：2016-3-8 14:21");
        map.put("type", "类型：4G数据投诉");
        map.put("address", "地区：宜昌-长阳县");
        list.add(map);

        map = new HashMap<String, String>();
        map.put("number", "编号：201603084332");
        map.put("time", "时间：2016-3-8 14:21");
        map.put("type", "类型：4G数据投诉");
        map.put("address", "地区：宜昌-长阳县");
        list.add(map);

        map = new HashMap<String, String>();
        map.put("number", "编号：201603084332");
        map.put("time", "时间：2016-3-8 14:21");
        map.put("type", "类型：4G数据投诉");
        map.put("address", "地区：宜昌-长阳县");
        list.add(map);

        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pending_order);

        mData = getDate();

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

                Intent intent = new Intent(PendingOrder.this, OrderDetail.class);

                startActivity(intent);

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

        ma.notifyDataSetChanged();

        checkButton();

        setPagenumber();
    }

    public void rightView(){

        index++;

        ma.notifyDataSetChanged();

        checkButton();

        setPagenumber();
    }


    public void checkButton(){


        int totalnumber = getTotalnumber();

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

        int totalnumber = getTotalnumber();

        tv1.setText("总数：" + totalnumber);
    }

    //显示页数
    public void setPagenumber(){

        int totalnumber = getTotalnumber();

        double pagenumber = Math.ceil((double) totalnumber / VIEW_COUNT);

       int pn = (int) pagenumber;

        tv2.setText("页数：" + (index + 1) + "/" + pn );
    }

    //获得工单的总数
    public int getTotalnumber(){

        return mData.size();

    }

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

            int ori = VIEW_COUNT * index;

            if(mData.size() - ori < VIEW_COUNT){
                return mData.size() - ori;
            }else{
                return VIEW_COUNT;
            }

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

            holder.number.setText((String)mData.get(position + index*VIEW_COUNT).get("number"));
            holder.time.setText((String)mData.get(position + index*VIEW_COUNT).get("time"));
            holder.type.setText((String) mData.get(position + index * VIEW_COUNT).get("type"));
            holder.address.setText((String)mData.get(position + index*VIEW_COUNT).get("address"));

            return convertView;
        }
    }

}
