package xiyou.mc.framework;

import android.content.Context;

public class ComponentManagerConfig {
    public Context context;

    public ComponentManagerConfig setContext(Context context) {
        this.context = context;
        return this;
    }
}
