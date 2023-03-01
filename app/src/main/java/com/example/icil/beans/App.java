package com.example.icil.beans;

import android.app.Application;
import android.os.StrictMode;

import com.example.icil.BuildConfig;

public class App extends Application {
    private static App instance;

    public static App getApplication() {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();
        return instance;
    }
}
