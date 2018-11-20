package com.mc.b.core.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mc.b.api.BService;
import com.mc.b.core.page.BActivity;

public class BServiceImpl implements BService {
    @Override
    public void startBActivity(Context context) {
        if (context instanceof Activity) {
            Intent intent = new Intent(context, BActivity.class);
            context.startActivity(intent);
        }
    }
}
