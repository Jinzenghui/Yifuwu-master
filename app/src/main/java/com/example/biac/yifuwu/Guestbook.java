package com.example.biac.yifuwu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Pojo.Message;

public class Guestbook extends Activity {

    private ListView lv;

    ChatAdapter ca;

    private List<Message> getData(){

        List<Message> msgList = new ArrayList<Message>();
        Message msg;

        msg = new Message();
        msg.setType(ChatAdapter.GUEST_TYPE);
        msg.setTime("2016/3/8 14:27");
        msg.setInfomation("上得了网， 打不了电话，快点解决");
        msgList.add(msg);


        msg = new Message();
        msg.setType(ChatAdapter.SYSTEM_TYPE);
        msg.setTime("2016/3/8  15:31");
        msg.setInfomation("用户，你好！你所在位置的基站发生故障，请耐心等待");
        msgList.add(msg);

        return msgList;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guestbook);

        lv = (ListView)findViewById(R.id.lv_data);

        ca = new ChatAdapter(this, getData());

        lv.setAdapter(ca);


    }

    public class ChatAdapter extends BaseAdapter {

        public static final int GUEST_TYPE = 0;
        public static final int SYSTEM_TYPE = 1;

        private LayoutInflater mInflater;

        private List<Message> myList;

        public ChatAdapter(Context context, List<Message> myList){

            this.myList = myList;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Object getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Message msg = myList.get(position);
            int type = getItemViewType(position);
            ViewHolderLeft holderLeft = null;
            ViewHolderRight holderRight = null;

            if(convertView == null){

                switch(type){

                    case GUEST_TYPE:
                        holderLeft = new ViewHolderLeft();
                        convertView = mInflater.inflate(R.layout.guestbookmessage, null);
                        holderLeft.guesttimeText = (TextView)convertView.findViewById(R.id.guesttime);

                        holderLeft.guesttimeText.setText(msg.getTime());

                        holderLeft.guestbookText = (TextView)convertView.findViewById(R.id.guestmessage);
                        holderLeft.guestbookText.setText(msg.getInfomation());
                        break;

                    case SYSTEM_TYPE:
                        holderRight = new ViewHolderRight();
                        convertView = mInflater.inflate(R.layout.systemmessage, null);

                        holderRight.systemtimeText = (TextView)convertView.findViewById(R.id.systemtime);
                        holderRight.systemtimeText.setText(msg.getTime());

                        holderRight.systemText = (TextView)convertView.findViewById(R.id.systemmessage);
                        holderRight.systemText.setText(msg.getInfomation());
                        break;

                    default:
                        break;
                }
            }else{

                switch(type){

                    case GUEST_TYPE:

                        holderLeft = (ViewHolderLeft) convertView.getTag();

                        holderLeft.guesttimeText.setText(msg.getTime());

                        holderLeft.guestbookText.setText(msg.getInfomation());

                        break;

                    case SYSTEM_TYPE:
                        holderRight = (ViewHolderRight)convertView.getTag();

                        holderRight.systemtimeText.setText(msg.getTime());

                        holderRight.systemText.setText(msg.getInfomation());
                        break;

                    default:
                        break;
                }

            }

            return convertView;
        }

        @Override
        public int getItemViewType(int position){

            Message msg = myList.get(position);
            int type = msg.getType();
            return type;
        }

        @Override
        public int getViewTypeCount(){
            return 2;
        }

        class ViewHolderLeft{

            private TextView guesttimeText;
            private TextView guestbookText;

        }

        class ViewHolderRight{

            private TextView systemtimeText;
            private TextView systemText;

        }

    }
}
