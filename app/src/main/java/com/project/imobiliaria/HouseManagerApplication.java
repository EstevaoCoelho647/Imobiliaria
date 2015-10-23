package com.project.imobiliaria;

import android.app.Application;

import com.project.imobiliaria.model.util.ApplicationUtil;

/**
 * Created by c1284520 on 16/10/2015.
 */
public class HouseManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setContext(getApplicationContext());
    }
}

