package com.example.biac.yifuwu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import Pojo.Login;
import Pojo.UserInfo;
import Pojo.UserLogin;
import Utils.NetUtils;

public class MainActivity extends Activity {

    private Button logInBtn;
    private EditText userName, password;
    private CheckBox remember_pwd, auto_login;
    private String userNameValue, passwordValue;
    private SharedPreferences sp;

    private android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = (EditText)findViewById(R.id.Username);
        password = (EditText)findViewById(R.id.Password);
        remember_pwd = (CheckBox)findViewById(R.id.rememberPassword);
        auto_login = (CheckBox)findViewById(R.id.autoLogin);

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
               userLogin(name, password, "loginurl", "userurl");


            }
        }

        logInBtn = (Button) findViewById(R.id.LoginButton);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();

                    if (remember_pwd.isChecked()) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD", passwordValue);
                        editor.commit();
                    }


                Intent intent = new Intent();
                intent.setClass(MainActivity.this, login.class);
                startActivity(intent);

//                    Intent intent = new Intent(MainActivity.this, login.class);
//                    startActivity(intent);
//                    userLogin(userNameValue, passwordValue, "loginurl", "userurl");

//                 else {
//                    Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();
//                }
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

    /*
    用户登录
     */
    public void userLogin(String name, String password, String loginUrl, String userUrl){
         /*
                登录流程
                 */
        UserLogin u = new UserLogin(name, password);

        final Gson gson = new Gson();


        final Application app = this.getApplication();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String result;
                Login login;
                if(response.isSuccessful()){

//                    Log.i("fl", response.body().charStream().toString());
                    login =  gson.fromJson(response.body().charStream(), Login.class);
                    Log.i("fl",login.toString());
                }else{
                    throw new IOException("Unexpected code " + response);
                }

                //判断result，成功数据绑定跳转或失败显示原因

                //如果登录成功则获取用户信息
                if(login.result == 0){

                    String uJson = "{user_id:"+login.data.getUserId()+"}";

                    try{
                        NetUtils.postJson("http://120.25.164.238:80/AndroidApp/login", uJson, app, new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {

                                //用户信息获取失败

                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String userInfoJson = response.body().toString();
                                UserInfo userInfo = gson.fromJson(userInfoJson, UserInfo.class);
                                //绑定数据并跳转
                                Intent intent = new Intent(MainActivity.this, login.class);
                                intent.putExtra("userInfo", userInfo);
                                startActivity(intent);
                            }
                        });





                    }catch (IOException e){
                        Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();
                    }


                }else{
                    //登录失败
//                    Looper.prepare();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }
        };


        Log.i("fl", gson.toJson(u));

        try{
            NetUtils.postJson("http://120.25.164.238:80/AndroidApp/login",gson.toJson(u), this.getApplication(), callback);

        }catch(IOException e){
            //登录失败处理
            Toast.makeText(MainActivity.this, "用户名或密码错误， 请重新登录", Toast.LENGTH_LONG).show();

        }

    }

}
