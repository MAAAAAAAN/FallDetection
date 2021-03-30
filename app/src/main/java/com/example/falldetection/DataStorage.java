package com.example.falldetection;

import android.content.Context;
import android.content.SharedPreferences;

public class DataStorage {

    private Context context;
    public String value;

    public DataStorage(Context context){
        this.context = context;
    }

    public void save(){
        SharedPreferences shp = context.getSharedPreferences("DataStorage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putString("data", value);
        editor.apply();
    }

    public String load(){
        SharedPreferences shp = context.getSharedPreferences("DataStorage", Context.MODE_PRIVATE);
        String v = shp.getString("data", "null");
        value = v;
        return value;
    }

}
