package Utils;

import android.app.Application;

import com.example.biac.yifuwu.MyApplication;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

/**
 * Created by fanlei on 16/3/15.
 */
public class NetUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void postJson(String url, RequestBody body, Application app, Callback callback) throws IOException {
        OkHttpClient client = ((MyApplication)app).getClient();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);

    }

    public static void getJson(String url, Application app, Callback callback) throws IOException{

        OkHttpClient client = ((MyApplication)app).getClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);

    }

    public static void getWeather(String url,Application app, Callback callback){
        OkHttpClient client = ((MyApplication)app).getClient();
        Request request = new Request.Builder().url(url).header("apikey", "c2b72ceb034408448d51f52dd2cc857c").build();
        client.newCall(request).enqueue(callback);
    }

}

