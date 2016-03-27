package com.example.biac.yifuwu;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import Pojo.RegisterCallbackInfo;
import Pojo.UserInfo;
import Utils.NetUtils;

public class Register extends AppCompatActivity {

    private EditText user_name_editText, work_station_editText, telep_num_editText, password_editText, confirm_pwd_editText;

    private String user_name, work_station, telep_num, password, confirm_pwd, sex="";

    private Button registerBtn;

    private RadioGroup sexGroup;

    private RadioButton male_radioButton, female_radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        listenInputType();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();

                if(user_name.equals("")){

                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("用户名不能为空！").setPositiveButton("确定", null).show();

                }else if(work_station.equals("")){

                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("负责工位不能为空！").setPositiveButton("确定", null).show();

                }else if(telep_num.equals("")){

                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("手机号码不能为空！").setPositiveButton("确定", null).show();

                }else if(sex.equals(""))
                {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("请选择性别！").setPositiveButton("确定", null).show();

                }else if(password.equals(""))
                {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("密码不能为空！").setPositiveButton("确定", null).show();

                }else if(! (password.length() == 8))
                {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("密码必须为8位数字或者字母组合，不含特殊字符！").setPositiveButton("确定", null).show();

                }else if(!password.equals(confirm_pwd)){
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("设置的密码和确认密码不一致！").setPositiveButton("确定", null).show();

                }else{

                    setRegisterInfo(user_name, password, work_station, telep_num, sex);

                }

            }
        });

    }

    public void initView(){

        user_name_editText = (EditText)findViewById(R.id.user_name);
        work_station_editText = (EditText)findViewById(R.id.work_station);
        telep_num_editText = (EditText)findViewById(R.id.telep_num);
        password_editText = (EditText)findViewById(R.id.password);
        confirm_pwd_editText = (EditText)findViewById(R.id.confirm_pwd);

        sexGroup = (RadioGroup)findViewById(R.id.choose_sex);

        male_radioButton = (RadioButton)findViewById(R.id.boy);
        female_radioButton = (RadioButton)findViewById(R.id.girl);

        registerBtn = (Button)findViewById(R.id.register);

        SpannableString user_name_span = new SpannableString("用户名或手机号");
        SpannableString pwd_span = new SpannableString("8位数字或者字母组合，不含特殊字符");

        AbsoluteSizeSpan abs_user_name_span = new AbsoluteSizeSpan(12, true);
        AbsoluteSizeSpan abs_pwd_span = new AbsoluteSizeSpan(12, true);

        user_name_span.setSpan(abs_user_name_span, 0, user_name_span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        pwd_span.setSpan(abs_pwd_span, 0, pwd_span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        user_name_editText.setHint(new SpannableString(user_name_span));
        password_editText.setHint(new SpannableString(pwd_span));

    }

    public void listenInputType(){

        password_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try{

                    String temp = s.toString();
                    String tem = temp.substring(temp.length()-1, temp.length());
                    char[] temC = tem.toCharArray();
                    int mid = temC[0];

                    if(mid >= 48 && mid <= 57){
                        return;
                    }else if(mid >= 65 && mid <= 90){
                        return;
                    }else if(mid > 97 && mid <= 122){
                        return;
                    }else{
                        s.delete(temp.length()-1, temp.length());
                        Toast.makeText(Register.this, "密码只能输入8为数字或者字母组合，不能含有特殊符号",Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        confirm_pwd_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try{
                    String temp = s.toString();
                    String tem = temp.substring(temp.length() - 1, temp.length());
                    char[] temC = tem.toCharArray();
                    int mid = temC[0];

                    if(mid >= 48 && mid <= 57){
                        return;
                    }else if(mid >= 65 && mid <= 90){
                        return;
                    }else if(mid > 97 && mid <= 122){
                        return;
                    }else{
                        s.delete(temp.length()-1, temp.length());
                        Toast.makeText(Register.this, "密码只能输入8为数字或者字母组合，不能含有特殊符号", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public void getInput(){

        user_name = user_name_editText.getText().toString();
        work_station = work_station_editText.getText().toString();
        telep_num = telep_num_editText.getText().toString();
        password = password_editText.getText().toString();
        confirm_pwd = confirm_pwd_editText.getText().toString();

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == male_radioButton.getId()) {

                    sex = male_radioButton.getText().toString();

                } else if (checkedId == female_radioButton.getId()) {

                    sex = female_radioButton.getText().toString();

                } else {
                    sex = "";
                }

            }
        });
    }

    //发送给后台用户的注册信息
    //传入参数：用户名：user_name; 用户密码：user_password; 负责工位：work_station; 手机号码：contact; 用户性别: user_gender;

    public void setRegisterInfo(final String user_name, final String user_password, String work_station, String contact, String user_gender){

        final Gson gson = new Gson();

        final Application app = this.getApplication();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                RegisterCallbackInfo registerCallbackInfo;

                if(response.isSuccessful()){

                    registerCallbackInfo = gson.fromJson(response.body().charStream(), RegisterCallbackInfo.class);

                }else{

                    throw new IOException("Unexpected code " + response);

                }

                if(registerCallbackInfo.getResult() == 0){

                    Toast.makeText(Register.this, registerCallbackInfo.getText() + "，正在登录....", Toast.LENGTH_SHORT).show();

                    RequestBody bodyLogin = new FormEncodingBuilder().add("user_name", user_name).add("user_password", user_password).build();

                    try{

                        NetUtils.postJson("http://120.27.106.26/app/login", bodyLogin, app, new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                            }

                            @Override
                            public void onResponse(Response response) throws IOException {

                                UserInfo userInfo = gson.fromJson(response.body().charStream(), UserInfo.class);

                                Intent intent = new Intent(Register.this, login.class);
                                intent.putExtra("userInfo", userInfo);
                                startActivity(intent);
                            }
                        });

                    }catch(IOException e){

                    }

                }else {
                    Toast.makeText(Register.this, registerCallbackInfo.getText() + "!", Toast.LENGTH_LONG).show();
                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("user_name", user_name).add("user_password", user_password).add("work_station", work_station).add("contact", contact).add("user_gender", user_gender).build();

        try{

            NetUtils.postJson("http:120.27.106.26/app/register", body, this.getApplication(), callback);

        }catch(IOException e){

        }
    }
}
