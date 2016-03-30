package com.example.biac.yifuwu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import Pojo.UserInfo;
import Pojo.UserLogin;
import Utils.NetUtils;

public class MainActivity extends Activity {

    private Button logInBtn, registerBtn;
    private AutoCompleteTextView userName;
    private EditText password;
    private CheckBox remember_pwd, auto_login;
    private String userNameValue, passwordValue;
    private SharedPreferences sp;

    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        sp = this.getSharedPreferences("userInfo2", Context.MODE_PRIVATE);

        initView();

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i("fl", sp.getString("USER_NAME", "null"));
//                String[] allUserName;
//                allUserName = sp.getStringSet("",null).toArray(new String[sp.getAll().size()]);
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,allUserName);
//
//                userName.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(remember_pwd.isChecked()) {
//                    password.setText(sp.getString(userName.getText().toString(), ""));
//                }
            }
        });

        if(sp.getBoolean("ISCHECK", false))
        {
            remember_pwd.setChecked(true);
            userName.setText(sp.getString("USER_NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));
            String name = sp.getString("USER_NAME", "");
            String password = sp.getString("PASSWORD", "");

            if(sp.getBoolean("AUTO_ISCHECK", false)){

                auto_login.setChecked(true);

                //登录流程
               userLogin(name, password);

            }
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取用户输入的用户名和密码
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();
//
//                if (remember_pwd.isChecked()) {
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("USER_NAME", userNameValue);
//                    editor.putString("PASSWORD", passwordValue);
//                    editor.commit();
//                }

                userLogin(userNameValue, passwordValue);

            }
        });

        remember_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (remember_pwd.isChecked()) {
                    sp.edit().putBoolean("ISCHECK", true).commit();
                } else {
                    sp.edit().putBoolean("ISCHECK", false).commit();
                }
            }
        });

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                } else {
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });

    }

    public void initView(){

        userName = (AutoCompleteTextView)findViewById(R.id.Username);

        password = (EditText)findViewById(R.id.Password);
        remember_pwd = (CheckBox)findViewById(R.id.rememberPassword);
        auto_login = (CheckBox)findViewById(R.id.autoLogin);

        logInBtn = (Button) findViewById(R.id.LoginButton);
        registerBtn = (Button)findViewById(R.id.registerButton);

    }

    /*
    用户登录
     */
    public void userLogin(String name, final String password){
         /*
                登录流程
                 */
        UserLogin u = new UserLogin(name, password);

        final Gson gson = new Gson();

        final Application app = this.getApplication();

        final String usernameTmp = name;

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResponse(Response response) throws IOException {

                UserInfo userInfo;

                if(response.isSuccessful()){

                    userInfo = gson.fromJson(response.body().charStream(), UserInfo.class);

                    Log.i("Jin_result", userInfo.getResult() + "");
//                    Log.i("Jin_text", userInfo.getText());
//                    Log.i("Jin-user_id", userInfo.getData().getUser_id()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getTotal_login_times()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getTotal_work_orders()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getPoints()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getUser_rank()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getTotal_login_days()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getLast_login_time()+"");
//                    Log.i("Jin-user_id", userInfo.getData().getWork_station()+"");

                }else{

                    throw new IOException("Unexpected code " + response);

                }

                if(userInfo.getResult() == 0){
//
                    if (remember_pwd.isChecked()) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD", passwordValue);
                        editor.commit();

                    }

                    Intent intent = new Intent(MainActivity.this, login.class);
                    intent.putExtra("userInfo", userInfo);
                    startActivity(intent);

                }else if(userInfo.getResult() == -1){

                    Log.i("Jin_result", userInfo.getResult() + "");
                    Log.i("Jin_text", userInfo.getText());

                }
            }
        };

        RequestBody body = new FormEncodingBuilder().add("user_name", u.getUser_name()).add("user_password", u.getUser_password()).build();

        try{
            NetUtils.postJson("http://120.27.106.26/app/login", body, this.getApplication(), callback);
        }catch(IOException e){
            //登录失败处理
            Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();

        }

    }

}
