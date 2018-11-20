package xiyou.mc.framework.tree.core.api;

public interface BundleFindServiceTarget {

    boolean removeService(String serviceApi);

    Object findService(String serviceApi);

    boolean isInitRoot();

    void initNoLazyRootMap();
}
