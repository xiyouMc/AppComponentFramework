package xiyou.mc.framework.tree.node;


import xiyou.mc.framework.tree.core.BundleCoreTarget;
import xiyou.mc.framework.tree.node.api.BundleFragmentElement;
import xiyou.mc.framework.tree.node.entity.RouterFragmentModel;

public class BundleFragmentElementImpl extends BundleCoreTarget implements BundleFragmentElement {
    @Override
    public void addFragmentData(RouterFragmentModel model) {
        getBundleNodesManager().registerFragmentNode(model);
    }
}
