package com.fl.service;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;

import Utils.Common;


public class LocationService extends Service implements LocationListener{
    private static final String TAG = "LocationSvc";
    private LocationManager locationManager;


    public LocationService() {
    }

    @Override
    public void onCreate(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Log.i("fl", "service启动");


        try{
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("fl", "最后地址"+location);

            updateWithNewLocation(location);



            if(locationManager.getProvider(LocationManager.NETWORK_PROVIDER)!=null)
            //获取定位信息
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000,10,this);
            else if(locationManager.getProvider(LocationManager.GPS_PROVIDER)!=null)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,this);
            else{


                Toast.makeText(this, "无法定位", Toast.LENGTH_LONG).show();

            }

            //根据经纬度获取城市信息

        }catch (SecurityException e){
            Toast.makeText(this,"定位失败",Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("fl", "get the current position" + location);
        Intent intent = new Intent();
        intent.putExtra(Common.LOCATION, location.toString());
        intent.setAction(Common.LOCATION_ACTION);
        try{
            //停止服务
            locationManager.removeUpdates(this);
        }catch(SecurityException e){

        }


        sendBroadcast(intent);
        stopSelf();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void updateWithNewLocation(Location location) {
        String latLongString;

        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "纬度:" + lat + "\n经度:" + lng;
        } else {
            latLongString = "无法获取地理信息";
        }
        List<Address> addList = null;
        Geocoder ge = new Geocoder(this);
        try {
            addList = ge.getFromLocation(30.511, 114.41, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(addList!=null && addList.size()>0){
            for(int i=0; i<addList.size(); i++){
                Address ad = addList.get(i);
                latLongString += "\n";
                latLongString += ad.getCountryName() + ";" + ad.getLocality();
            }
        }

        Log.i("fl", latLongString);
    }
}

