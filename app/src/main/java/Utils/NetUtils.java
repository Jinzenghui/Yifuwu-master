package Utils;

import android.app.Application;
import android.content.Context;

import com.example.biac.yifuwu.MyApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by fanlei on 16/3/15.
 */
public class NetUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void postJson(String url, String json, Application app, Callback callback) throws IOException {
        OkHttpClient client = ((MyApplication)app).getClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
//        if(response.isSuccessful()){
//            return response.body().string();
//        }else{
//            throw new IOException("Unexpected code " + response);
//        }

    }

    public static void getWeather(String url,Application app, Callback callback){
        OkHttpClient client = ((MyApplication)app).getClient();
        Request request = new Request.Builder().url(url).header("apikey", "c2b72ceb034408448d51f52dd2cc857c").build();
        client.newCall(request).enqueue(callback);
    }

}
