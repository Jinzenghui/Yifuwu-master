package com.example.biac.yifuwu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Pojo.AcceptOrderMessage;
import Pojo.PingStatu;
import Utils.NetUtils;

public class acknowledgement extends Activity {

    private Spinner spinner;

    private String work_order_id;

    private Button testBtn;

    private Button submitBtn, saveBtn, backBtn;

    private RadioGroup ifSolvedGroup, signalTypeGroup;

    private RadioButton solvedRadioBtn, not_solvedRadioBtn, is_2GRadioBtn, is_3GRadioBtn, is_4GRadioBtn;

    private TextView lonTextView, latTextView, signalStandardTextView, signalQualityTextView, success_rateTextView, time_delayTextView;

    private EditText remarkEditText;

    private String ifSolved;
    private String signalType;

    private String problemType;

    private PingStatu pingStatu;


    private static final String[] type = {"A", "B", "C", "D", "E"};

    private ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_acknowledgement);

        initView();

        work_order_id = (String)getIntent().getStringExtra("Work_Order_Id");

        spinner = (Spinner)findViewById(R.id.problem_type);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                problemType = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ifSolvedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == solvedRadioBtn.getId()) {

                    ifSolved = "是";

                } else if (checkedId == not_solvedRadioBtn.getId()) {

                    ifSolved = "否";

                }
            }
        });

        signalTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == is_2GRadioBtn.getId()){

                    signalType = "2G";

                }else if(checkedId == is_3GRadioBtn.getId()){

                    signalType = "3G";

                }else if(checkedId == is_4GRadioBtn.getId()){

                    signalType = "4G";

                }

            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pingStatu = ping();

                success_rateTextView.setText(pingStatu.getSuccessRate()+"");
                time_delayTextView.setText(pingStatu.getDelay()+"");

            }
        });






    }

    public void initView(){

        submitBtn = (Button)findViewById(R.id.btnLeft);
        saveBtn = (Button)findViewById(R.id.save);
        backBtn = (Button)findViewById(R.id.btnRight);

        ifSolvedGroup = (RadioGroup)findViewById(R.id.ifsolved);
        signalTypeGroup = (RadioGroup)findViewById(R.id.signaltype);

        solvedRadioBtn = (RadioButton)findViewById(R.id.eliminated);
        not_solvedRadioBtn = (RadioButton)findViewById(R.id.not_eliminated);

        is_2GRadioBtn = (RadioButton)findViewById(R.id.is2g);
        is_3GRadioBtn = (RadioButton)findViewById(R.id.is3g);
        is_4GRadioBtn = (RadioButton)findViewById(R.id.is4g);

        testBtn = (Button)findViewById(R.id.test);

        lonTextView = (TextView)findViewById(R.id.longitude);
        latTextView = (TextView)findViewById(R.id.latitude);

        signalStandardTextView = (TextView)findViewById(R.id.signalstandard);
        signalQualityTextView = (TextView)findViewById(R.id.signalquality);

        success_rateTextView = (TextView)findViewById(R.id.success_rate);
        time_delayTextView = (TextView)findViewById(R.id.time_delay);

        remarkEditText = (EditText)findViewById(R.id.remark_infomation);

    }

    public static final PingStatu ping() {

        String result = null;
        int successTimes = 0;
        double delay = 0;

        try {

            String ip = "www.baidu.com";



            for(int i=0;i<4;i++){
                Process p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -w 1 " + ip);


                // 读取ping的内容，可不加。

                InputStream input = p.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(input));

                StringBuffer stringBuffer = new StringBuffer();

                String content = "";

                while ((content = in.readLine()) != null) {

                    stringBuffer.append(content);

                }

                Log.i("TTT", "result content : " + stringBuffer.toString());





                int status = p.waitFor();

                if (status == 0) {

                    result = "successful~";

                    //获取ping迟延
                    String time = "";
                    Pattern pattern = Pattern.compile("time=(.+?)ms");
                    Matcher matcher = pattern.matcher(stringBuffer.toString());
                    if(matcher.find()){
                        time = matcher.group(1);
                    }

                    successTimes++;
                    delay += Double.parseDouble(time);
                    Log.i("fl","result"+time);

                } else {

                    result = "failed~ cannot reach the IP address";

                }
            }

            // PING的状态

        } catch (IOException e) {

            result = "failed~ IOException";

        } catch (InterruptedException e) {

            result = "failed~ InterruptedException";

        } finally {

            Log.i("TTT", "result = " + result);

        }

        PingStatu statu = new PingStatu();
        if(successTimes != 0){
            statu.delay = delay/successTimes;
            statu.successRate = ((double)successTimes/4.0);
        }else{
            statu.successRate = 0;
            statu.delay = 0;
        }

        Log.i("fl", statu.successRate+"delay:"+statu.delay);


        return statu;

    }

    public void setSubmitData(String work_order_id, String is_handled, String network_type, String problem_type,
                              String actual_lat, String actual_lon, String actual_network, String ecio,
                              String success_rate, String time_delay, String remark){

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

                        Toast.makeText(acknowledgement.this, "释放成功", Toast.LENGTH_SHORT).show();

                    }else if(acceptOrderMessage.getResult() == -1){

                        Toast.makeText(acknowledgement.this, "释放失败" + acceptOrderMessage.getText(), Toast.LENGTH_LONG).show();

                    }

                }else{
                    throw new IOException("Unexpected code " + response);
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("work_order_id", work_order_id).add("is_handled", is_handled).add("network_type", network_type)
                .add("problem_type", problem_type).add("actual_lat", actual_lat).add("actual_lon", actual_lon).add("actual_network",actual_network).add("ecio",ecio)
                .add("success_rate",success_rate).add("time_delay",time_delay).add("remark", remark)
                .build();

        try{

            NetUtils.postJson("http://120.27.106.26/app/submitData", body, this.getApplication(), callback);

        }catch(IOException e){

            Toast.makeText(acknowledgement.this, "接单失败", Toast.LENGTH_LONG).show();

        }
    }




}
