package com.project.imobiliaria.model.util;

import android.content.Context;

/**
 * Created by c1284520 on 16/10/2015.
 */
public final class ApplicationUtil{

    private static Context context;

    private static Context applicationContext;

    private ApplicationUtil(){

    }

    public static void setContext(Context context){
        ApplicationUtil.context = context;
    }

    public static Context getContext() {
        return ApplicationUtil.context;
    }
}
