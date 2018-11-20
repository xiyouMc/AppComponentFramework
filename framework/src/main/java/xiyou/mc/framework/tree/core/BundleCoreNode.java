package xiyou.mc.framework.tree.core;

import xiyou.mc.framework.tree.core.api.BundleFindServiceTarget;

public interface BundleCoreNode extends BundleFindServiceTarget {
    BundleCoreNode getParent();

    void setParent(BundleCoreNode parent);

    boolean addChild(BundleCoreNode child);

    boolean removeChild(BundleCoreNode child);
}
