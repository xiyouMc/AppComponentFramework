package xiyou.mc.framework.tree.node.api;

import xiyou.mc.framework.tree.core.BundleCoreNode;
import xiyou.mc.framework.tree.node.entity.RouterModuleServiceModel;

public interface BundleServiceElement extends BundleCoreNode {
    void addServiceData(RouterModuleServiceModel moduleServiceModel);
}
