package com.example.college;

import android.app.Application;

import com.onesignal.OneSignal;

public class CollegeApp extends Application {
    private static final String ONESIGNAL_APP_ID = "7a0f0ae7-097f-4e3c-941a-ef749c864f48";
    @Override
    public void onCreate() {
        super.onCreate();
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
