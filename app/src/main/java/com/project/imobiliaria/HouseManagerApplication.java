package com.project.imobiliaria;

import android.app.Application;

import com.project.imobiliaria.model.util.ApplicationUtil;

public class HouseManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setContext(getApplicationContext());
    }
}

