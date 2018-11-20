package xiyou.mc.framework.tree.node.entity;

/**
 * Created by xiyouMc on 01/11/2017.
 */

public class BundleMapModel {
    private static final String TAG = "BundleMapModel";
    /**
     * Router Map class path
     */
    private String routerMapClassPath;
    /**
     * 当前 bundle routerMap OnCreate 是否被 Lazy 加载
     */
    private boolean lazy = true;

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }


    public String getRouterMapClassPath() {
        return routerMapClassPath;
    }

    public void setRouterMapClassPath(String routerMapClassPath) {
        this.routerMapClassPath = routerMapClassPath;
    }

}
