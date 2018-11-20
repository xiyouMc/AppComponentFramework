package xiyou.mc.framework.tree.core;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xiyou.mc.framework.tree.core.manager.BundleNodesManagerImpl;
import xiyou.mc.framework.tree.core.manager.api.BundleNodesManager;

public abstract class BundleCoreTarget implements BundleCoreNode {
    private BundleCoreNode parent;
    private List<BundleCoreNode> children;
    private BundleNodesManager bundleNodesManager;

    public BundleCoreTarget() {
        parent = null;
        children = new ArrayList<>();
        bundleNodesManager = new BundleNodesManagerImpl();
    }

    public BundleNodesManager getBundleNodesManager() {
        return bundleNodesManager;
    }

    @Override
    public BundleCoreNode getParent() {
        return parent;
    }

    @Override
    public void setParent(BundleCoreNode parent) {
        if (this.parent == parent) {
            return;
        }
        if (this.parent != null) {
            this.parent.removeChild(this);
        }

        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    @Override
    public boolean addChild(BundleCoreNode child) {
        if (child == null) {
            return false;
        }
        for (BundleCoreNode target : children) {
            if (target.equals(child)) {
                return false;
            }
        }
        children.add(child);
        child.setParent(this);
        return false;
    }

    @Override
    public boolean removeChild(BundleCoreNode child) {
        if (child == null) {
            return false;
        }

        Iterator<BundleCoreNode> iterator = children.iterator();
        while (iterator.hasNext()) {
            BundleCoreNode target = iterator.next();
            if (target.equals(child)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeService(String serviceApi) {
        boolean isRemoved = bundleNodesManager.removeService(serviceApi);
        if (!isRemoved) {
            if (children != null) {
                for (BundleCoreNode node : children) {
                    boolean isRemove = node.removeService(serviceApi);
                    if (isRemove) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        }
        return isRemoved;
    }

    @Override
    public Object findService(String serviceApi) {
        Object impl = bundleNodesManager.findService(serviceApi);
        if (impl == null) {
            /**
             * foreach child
             */
            if (children != null) {
                for (BundleCoreNode node : children) {
                    impl = node.findService(serviceApi);
                    if (impl != null) {
                        //foreach parent is init root
                        BundleCoreNode bundleCoreNode = node.getParent();
                        while (bundleCoreNode != null && !bundleCoreNode.isInitRoot()) {
                            bundleCoreNode = bundleCoreNode.getParent();
                        }
                        return impl;
                    }
                }
            }
        }
        return impl;
    }

    @Override
    public boolean isInitRoot() {
        return bundleNodesManager.isInitRoot();
    }

    @Override
    public void initNoLazyRootMap() {
        bundleNodesManager.initNoLazyRootMap();
        //foreach root map is init
        for (BundleCoreNode node : children) {
            node.initNoLazyRootMap();
        }
    }
}
