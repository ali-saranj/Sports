package com.example.sports;

import android.app.Application;

public class G extends Application {
    public static int set ,cunter;
    @Override
    public void onCreate() {
        super.onCreate();

        set = 20;
        cunter = 5;
    }
}
