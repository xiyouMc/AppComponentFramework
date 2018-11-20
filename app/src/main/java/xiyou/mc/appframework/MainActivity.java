package xiyou.mc.appframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mc.a.api.AService;

import xiyou.mc.framework.ComponentManager;
import xiyou.mc.framework.ComponentManagerConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ComponentManagerConfig config = new ComponentManagerConfig().setContext(this);
        ComponentManager.initBundleTree(config);
        findViewById(R.id.startA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentManager.getService(AService.class).startAactivity(MainActivity.this);
            }
        });
    }
}
