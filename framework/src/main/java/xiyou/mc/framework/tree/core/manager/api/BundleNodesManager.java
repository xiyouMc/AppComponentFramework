package xiyou.mc.framework.tree.core.manager.api;

import xiyou.mc.framework.tree.core.api.BundleFindServiceTarget;
import xiyou.mc.framework.tree.node.entity.BundleMapModel;
import xiyou.mc.framework.tree.node.entity.RouterFragmentModel;
import xiyou.mc.framework.tree.node.entity.RouterModuleServiceModel;

public interface BundleNodesManager extends BundleFindServiceTarget {
    void registerRootNode(BundleMapModel bundleMapModel);

    boolean registerServiceNode(RouterModuleServiceModel routerModuleServiceModel);

    boolean registerFragmentNode(RouterFragmentModel routerFragmentModel);
}
