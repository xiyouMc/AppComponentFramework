package com.mc.a.core.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mc.a.api.AService;
import com.mc.a.core.page.Aactivity;

public class AServiceImpl implements AService {
    @Override
    public void startAactivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent();
            intent.setClass(context, Aactivity.class);
            context.startActivity(intent);
        }
    }
}
