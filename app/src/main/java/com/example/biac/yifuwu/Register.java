package com.example.biac.yifuwu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText user_name_editText, work_station_editText, telep_num_editText, password_editText, confirm_pwd_editText;

    private String user_name, work_station, telep_num, password, confirm_pwd, sex="";

    private Button registerBtn;

    private RadioGroup sexGroup;

    private RadioButton male_radioButton, female_radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        listenInputType();

//        Log.i("password", password);
//        Log.i("Jin-Confirm-password", confirm_pwd);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();

                if(user_name.equals("")){

                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("用户名不能为空！").setPositiveButton("确定", null).show();

                }

                if(work_station.equals("")){
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("负责工位不能为空！").setPositiveButton("确定", null).show();
                }

                if(telep_num.equals("")){
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("手机号码不能为空！").setPositiveButton("确定", null).show();
                }

                if(sex.equals(""))
                {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("请选择性别！").setPositiveButton("确定", null).show();
                }

                if(password.equals(""))
                {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("密码不能为空！").setPositiveButton("确定", null).show();
                }

                if(! (password.length() == 8))
                {
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("密码必须为8位数字或者字母组合，不含特殊字符！").setPositiveButton("确定", null).show();
                }
                else if(!password.equals(confirm_pwd)){
                    new AlertDialog.Builder(Register.this).setTitle("提示").setMessage("设置的密码和确认密码不一致！").setPositiveButton("确定", null).show();
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

                if(checkedId == male_radioButton.getId()){

                    sex = male_radioButton.getText().toString();

                }else if(checkedId == female_radioButton.getId()){

                    sex = female_radioButton.getText().toString();

                }else{
                    sex = "";
                }

            }
        });

    }

}
