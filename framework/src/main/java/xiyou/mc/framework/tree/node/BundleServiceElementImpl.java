package xiyou.mc.framework.tree.node;


import xiyou.mc.framework.tree.core.BundleCoreTarget;
import xiyou.mc.framework.tree.node.api.BundleServiceElement;
import xiyou.mc.framework.tree.node.entity.RouterModuleServiceModel;

public class BundleServiceElementImpl extends BundleCoreTarget implements BundleServiceElement {
    @Override
    public void addServiceData(RouterModuleServiceModel moduleServiceModel) {
        getBundleNodesManager().registerServiceNode(moduleServiceModel);
    }
}
