package com.example.ubuntu.myapplication.SingletonManager;


import android.content.Context;

import com.example.ubuntu.myapplication.Utils.DesignationsManager;

public class ObjectFactory {

    private static ObjectFactory instance = null;


    private DesignationsManager orderManager = null;

    private ObjectFactory() {
    }

    public synchronized static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public DesignationsManager getOrderManager(Context context) {
        if (orderManager == null) {
            orderManager = new DesignationsManager(context);
        }
        return orderManager;
    }
}