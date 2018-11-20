package xiyou.mc.framework.tree.node.entity;

/**
 * Created by xiyouMc on 01/11/2017.
 */

public class RouterActivityModel {
    private String schema;
    private String path;
    private boolean lazy = true;

    public boolean lazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
