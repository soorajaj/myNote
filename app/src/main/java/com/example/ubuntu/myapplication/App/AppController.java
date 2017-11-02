package com.example.ubuntu.myapplication.App;


/**
 * Created by ubuntu on 16/10/17.
 */

public class AppController extends android.app.Application {
    private static AppController mInstance;
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
