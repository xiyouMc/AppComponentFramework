package xiyou.mc.framework.tree.root;


import java.util.Stack;

import xiyou.mc.framework.tree.core.BundleCoreTarget;
import xiyou.mc.framework.tree.root.api.BundleRoot;
import xiyou.mc.framework.tree.session.api.BundleSession;

/**
 * top root
 */
public class BundleRootImpl extends BundleCoreTarget implements BundleRoot {
    private Stack<BundleSession> sessions;

    public BundleRootImpl() {
        sessions = new Stack<>();
    }

    @Override
    public void initBundleSession() {
        synchronized (sessions) {
            for (BundleSession session : sessions) {
                session.initNoLazyRootMap();
            }
        }

    }

    @Override
    public boolean addBundleSession(BundleSession session) {
        if (session == null) {
            return false;
        }
        synchronized (sessions) {
            for (BundleSession s : sessions) {
                if (s.equals(session)) {
                    return false;
                }
            }
            session.setParent(this);
            sessions.add(session);
            return true;
        }
    }
}
