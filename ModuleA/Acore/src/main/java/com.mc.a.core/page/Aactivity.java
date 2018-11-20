package com.mc.a.core.page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mc.a.core.R;
import com.mc.b.api.BService;

import xiyou.mc.framework.ComponentManager;

public class Aactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aactivity);
        findViewById(R.id.startBActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentManager.getService(BService.class).startBActivity(Aactivity.this);
            }
        });
    }
}

