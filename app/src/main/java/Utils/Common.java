package Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import Pojo.WeatherInfo;

/**
 * Created by fanlei on 16/3/17.
 */
public class Common {

    public static final String LOCATION = "location";
    public static final String LOCATION_ACTION = "location_action";


    /*
    http://www.heweather.com/documents/cn-city-list
    获取城市天气代码
     */
    public static String getCityName(String city){
        if(city.contains("市")||city.contains("区")||city.contains("县")||city.contains("镇")){
            String result =  city.substring(0,city.length()-1);
            Log.i("fl","城市代码为"+result);
            return result;
        }
        return city;

    }

    /*
    处理天气json数据
     */
    public static String getWeather(String json){

        String weatherInfo_String = " ";

        WeatherInfo weatherInfo = new WeatherInfo();
        try{


            JSONObject dataOfJson = new JSONObject(json);
            JSONArray results = dataOfJson.getJSONArray("HeWeather data service 3.0");
            JSONObject result0 = results.getJSONObject(0);

            //获取空气质量
            JSONObject aqi = result0.getJSONObject("aqi");
            String qlty = aqi.getJSONObject("city").getString("qlty");
            weatherInfo.setQlty(qlty);

            //获取城市信息
            JSONObject basic = result0.getJSONObject("basic");
            String city = basic.getString("city");
            weatherInfo.setCity(city);

            //获取温度
            JSONArray dailyFts = result0.getJSONArray("daily_forecast");
            JSONObject dailyFt = dailyFts.getJSONObject(0);
            JSONObject tmp = dailyFt.getJSONObject("tmp");
            String max = tmp.getString("max");
            String min = tmp.getString("min");
            weatherInfo.setMaxtmp(max);
            weatherInfo.setMintmp(min);

            //获取建议
            JSONObject sugg = result0.getJSONObject("suggestion");
            JSONObject drsg = sugg.getJSONObject("drsg");
            String txt = drsg.getString("txt");
            weatherInfo.setSuggest(txt);

            weatherInfo_String = "          " + city + "市，今天" + "最高温度：" + max + "°C" + "," + "最低温度：" + min + "°C," + " " + qlty + " " + txt;

        }catch (Exception e){

            e.printStackTrace();

        }

        return weatherInfo_String;

    }

}
