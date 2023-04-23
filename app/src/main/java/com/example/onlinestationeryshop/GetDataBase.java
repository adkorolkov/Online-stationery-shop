package com.example.onlinestationeryshop;

import android.util.Log;

import androidx.room.Room;

public class GetDataBase {

    private static GetDataBase instance;
    private MainActivity mainActivity;
    private DataBase db;
    private ConfigDao configDao;

    public ConfigDao getConfigDao() {
        return configDao;
    }


    public static synchronized GetDataBase getInstance() {
        if (instance == null) {
            instance = new GetDataBase();
            instance.mainActivity = new MainActivity();
            Log.d("getDatabase", "bbb");
            Log.d("getDatabase", instance.mainActivity+"bvvvv");
            try {
                Log.d("getDatabase", instance.mainActivity.getApplicationContext() + "bvvvv");
            }
            catch (Exception e){
                Log.d("getDatabase", e.toString()+"www");
            }
            instance.db = Room.databaseBuilder(instance.mainActivity.getApplicationContext(), DataBase.class, "stationery").allowMainThreadQueries().build();
            instance.configDao = instance.db.configDao();

        }
        return instance;
    }

}
