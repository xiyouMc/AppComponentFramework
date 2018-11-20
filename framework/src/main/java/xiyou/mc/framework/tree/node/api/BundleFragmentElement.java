package xiyou.mc.framework.tree.node.api;

import xiyou.mc.framework.tree.core.BundleCoreNode;
import xiyou.mc.framework.tree.node.entity.RouterFragmentModel;

public interface BundleFragmentElement extends BundleCoreNode {
    void addFragmentData(RouterFragmentModel model);
}
