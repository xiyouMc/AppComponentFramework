package xiyou.mc.framework.tree.root.api;


import xiyou.mc.framework.tree.core.BundleCoreNode;
import xiyou.mc.framework.tree.session.api.BundleSession;

public interface BundleRoot extends BundleCoreNode {

    boolean addBundleSession(BundleSession session);

    void initBundleSession();
}
