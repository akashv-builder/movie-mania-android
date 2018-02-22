package com.moviemania.akash.projectwork;

import android.app.Application;
import android.content.Context;

/**
 * Created by Akash on 13-Aug-16.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;
   public static final String API_KEY="ebbbc775ac71b9b9bf5363c1d4072724";
    //public static final String API_KEY="CEB62C911B3138271C96292B89EFCD25";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance()

    {

        return sInstance;

    }

    public static Context getAppContext()
    {

      return sInstance.getApplicationContext();
    }
}