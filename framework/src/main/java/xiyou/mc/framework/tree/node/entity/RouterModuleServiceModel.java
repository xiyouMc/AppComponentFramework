package xiyou.mc.framework.tree.node.entity;

/**
 * Created by xiyouMc on 01/11/2017.
 */

public class RouterModuleServiceModel {
    private String api;
    private String impl;
    private boolean lazy = true;
    private boolean preLoad = true;

    public boolean isPreLoad() {
        return preLoad;
    }

    public void setPreLoad(boolean preLoad) {
        this.preLoad = preLoad;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }
}
