package xiyou.mc.framework.tree.session;

import java.util.Iterator;
import java.util.Stack;

import xiyou.mc.framework.tree.core.BundleCoreTarget;
import xiyou.mc.framework.tree.node.api.BundleFragmentElement;
import xiyou.mc.framework.tree.node.api.BundleServiceElement;
import xiyou.mc.framework.tree.node.entity.BundleMapModel;
import xiyou.mc.framework.tree.session.api.BundleSession;

public class BundleSessionImpl extends BundleCoreTarget implements BundleSession {
    private Stack<BundleServiceElement> bundleServiceElements;
    private Stack<BundleFragmentElement> bundleFragmentElements;

    public BundleSessionImpl(BundleMapModel bundleMapModel) {
        this.bundleFragmentElements = new Stack<>();
        this.bundleServiceElements = new Stack<>();
        getBundleNodesManager().registerRootNode(bundleMapModel);
    }

    @Override
    public boolean addService(BundleServiceElement element) {
        if (element == null) {
            return false;
        }
        synchronized (bundleServiceElements) {
            for (BundleServiceElement serviceElement : bundleServiceElements) {
                if (serviceElement.equals(element)) {
                    return false;
                }
            }
        }
        element.setParent(this);
        bundleServiceElements.add(element);
        return true;
    }

//    @Override
//    public boolean addFragment(BundleFragmentElement element) {
//        if (element == null) {
//            return false;
//        }
//        synchronized (bundleFragmentElements) {
//            for (BundleFragmentElement fragmentElement : bundleFragmentElements) {
//                if (fragmentElement.equals(element)) {
//                    return false;
//                }
//            }
//        }
//        element.setParent(this);
//        bundleFragmentElements.add(element);
//        return true;
//    }

    @Override
    public boolean removeService(BundleServiceElement element) {
        if (element == null) {
            return false;
        }
        BundleServiceElement removeServiceElement = null;
        synchronized (bundleServiceElements) {
            Iterator<BundleServiceElement> iterator = bundleServiceElements.iterator();
            while (iterator.hasNext()) {
                BundleServiceElement bundleServiceElement = iterator.next();
                if (bundleServiceElement.equals(element)) {
                    iterator.remove();
                    removeServiceElement = bundleServiceElement;
                    break;
                }
            }
        }
// TODO: 11/17/18 release removeServiceElement
        if (removeServiceElement != null) {
            removeServiceElement.setParent(null);
        }

        return removeServiceElement != null;
    }

//    @Override
//    public boolean removeFragment(BundleFragmentElement element) {
//        if (element == null) {
//            return false;
//        }
//        BundleFragmentElement removeFragmentElement = null;
//        synchronized (bundleFragmentElements) {
//            Iterator<BundleFragmentElement> iterator = bundleFragmentElements.iterator();
//            while (iterator.hasNext()) {
//                BundleFragmentElement bundleFragmentElement = iterator.next();
//                if (bundleFragmentElement.equals(element)) {
//                    iterator.remove();
//                    removeFragmentElement = bundleFragmentElement;
//                    break;
//                }
//            }
//        }
//// TODO: 11/17/18 release removeFragmentElement
//        if (removeFragmentElement != null) {
//            removeFragmentElement.setParent(null);
//        }
//
//        return removeFragmentElement != null;
//    }
}
