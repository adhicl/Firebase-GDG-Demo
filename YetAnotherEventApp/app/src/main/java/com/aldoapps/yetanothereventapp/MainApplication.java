package com.aldoapps.yetanothereventapp;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

/**
 * Created by aldo on 10/27/16.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Fresco.initialize(this);
    }
}
