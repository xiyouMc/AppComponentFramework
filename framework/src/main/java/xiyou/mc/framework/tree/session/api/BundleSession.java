package xiyou.mc.framework.tree.session.api;


import xiyou.mc.framework.tree.core.BundleCoreNode;
import xiyou.mc.framework.tree.node.api.BundleServiceElement;

public interface BundleSession extends BundleCoreNode {

    boolean addService(BundleServiceElement element);

    boolean removeService(BundleServiceElement element);

}