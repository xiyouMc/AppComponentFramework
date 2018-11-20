package xiyou.mc.framework.tree.core.manager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import xiyou.mc.framework.Component;
import xiyou.mc.framework.tree.core.manager.api.BundleNodesManager;
import xiyou.mc.framework.tree.node.entity.BundleMapModel;
import xiyou.mc.framework.tree.node.entity.RouterFragmentModel;
import xiyou.mc.framework.tree.node.entity.RouterModuleServiceModel;

public class BundleNodesManagerImpl implements BundleNodesManager {
    private static final String TAG = "BundleNodesManagerImpl";
    private BundleMapModel bundleRootNode;
    private Set<RouterModuleServiceModel> routerModuleServiceLazyModelSet;
    private ConcurrentHashMap<String, Object> serviceManagers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Class<? extends Fragment>> fragmentClsManagers = new ConcurrentHashMap<>();
    private Set<RouterFragmentModel> routerFragmentLazyModelSet;

    private Set<String> initedRouterMap = new HashSet<>();

    public BundleNodesManagerImpl() {
        routerModuleServiceLazyModelSet = new HashSet<>();
        routerFragmentLazyModelSet = new HashSet<>();
    }

    @Override
    public boolean isInitRoot() {
        if (bundleRootNode == null) {
            return false;
        }
        /**
         * Judge is Init Root
         */
        if (bundleRootNode.isLazy()) {
            newInstanceBundleMap();
        }
        return true;
    }


    @Override
    public boolean removeService(String serviceApi) {
        if (serviceManagers != null) {
            Object serviceImpl = serviceManagers.remove(serviceApi);
            Log.d(TAG, " need remove impl:" + serviceImpl);
            if (serviceImpl != null) {
                //rebuild service
                RouterModuleServiceModel moduleServiceModel = new RouterModuleServiceModel();
                moduleServiceModel.setApi(serviceApi);
                moduleServiceModel.setImpl(serviceImpl.getClass().getName());
                routerModuleServiceLazyModelSet.add(moduleServiceModel);
                findService(serviceApi);
                serviceImpl = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public Object findService(String serviceApi) {
        if (bundleRootNode != null) {
            return null;
        }
        if (serviceManagers.containsKey(serviceApi)) {
            return serviceManagers.get(serviceApi);
        } else {
            Iterator<RouterModuleServiceModel> iterator = routerModuleServiceLazyModelSet.iterator();
            while (iterator.hasNext()) {
                RouterModuleServiceModel service = iterator.next();
                if (service.getApi().equals(serviceApi)) {
                    iterator.remove();
                    Object impl = saveService(service);
                    Log.d(TAG, "new impl:" + impl);
                    if (impl != null) return impl;
                }
            }
            return null;
        }
    }

//    @Override
//    public Class<? extends Fragment> findLazyFragment(String schema) {
//        if (bundleRootNode != null) {
//            Log.d(TAG, "current build root:" + bundleRootNode.getRouterMapClassPath());
//            return null;
//        }
//        if (fragmentClsManagers.containsKey(schema)) {
//            return fragmentClsManagers.get(schema);
//        } else {
//            Iterator<RouterFragmentModel> iterator = routerFragmentLazyModelSet.iterator();
//            while (iterator.hasNext()) {
//                RouterFragmentModel fragmentModel = iterator.next();
//                Log.d(TAG, "loop current fragment:" + fragmentModel.getSchema());
//                if (fragmentModel.getSchema().equals(schema)) {
//                    iterator.remove();
//                    Class cls = addFragmentMapping(fragmentModel);
//                    if (cls != null) {
//                        return cls;
//                    }
//                }
//            }
//        }
//        return null;
//    }

    @Nullable
    private Object saveService(RouterModuleServiceModel service) {
        try {
            Class cls = Class.forName(service.getImpl());
            Object impl = cls.newInstance();
            serviceManagers.put(service.getApi(), impl);
            return impl;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException", e);
        } catch (InstantiationException e) {
            Log.e(TAG, "InstantiationException:", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException:", e);
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException:", e);
        }
        return null;
    }

    @Override
    public void registerRootNode(BundleMapModel bundleMapModel) {
        bundleRootNode = bundleMapModel;
//        if (!bundleMapModel.isLazy()) {
//            newInstanceBundleMap(bundleRootNode);
//        }
    }

    @Override
    public void initNoLazyRootMap() {
        if (bundleRootNode != null && !bundleRootNode.isLazy()) {
            newInstanceBundleMap();
        }
    }

    private void newInstanceBundleMap() {
        if (initedRouterMap.contains(bundleRootNode.getRouterMapClassPath())) {
            return;
        }
        initedRouterMap.add(bundleRootNode.getRouterMapClassPath());
        Class cls = null;
        try {
            cls = Class.forName(bundleRootNode.getRouterMapClassPath());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Router Exception:" + e.getMessage());
        }
        if (cls == null) {
            Log.e(TAG, bundleRootNode.getRouterMapClassPath() + " is null");
            return;
        }
        try {
            long startOnCreateTime = System.currentTimeMillis();

            Component router = (Component) cls.newInstance();
            Log.d(TAG, "bundleRootNode.getRouterMapClassPath():" + bundleRootNode.getRouterMapClassPath());
            Log.d(TAG, "(AdvanceRouterMapXML) cls.newInstance() timestamp:" + (System.currentTimeMillis() - startOnCreateTime));
            router.onCreate();
            Log.d(TAG, "router.onCreate() timestamp:" + (System.currentTimeMillis() - startOnCreateTime));
        } catch (InstantiationException e) {
            Log.e(TAG, "prepare exception InstantiationException", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "prepare exception IllegalAccessException", e);
        } catch (ClassCastException e) {
            Log.e(TAG, "ClassCastException", e);
        }
    }


    @Override
    public synchronized boolean registerServiceNode(RouterModuleServiceModel routerModuleServiceModel) {
        if (routerModuleServiceModel == null) {
            return false;
        }
        if (routerModuleServiceModel.isLazy()) {
            if (routerModuleServiceLazyModelSet.contains(routerModuleServiceModel)) {
                return false;
            }
            routerModuleServiceLazyModelSet.add(routerModuleServiceModel);
        } else {
            saveService(routerModuleServiceModel);
        }
        return true;
    }

    @Override
    public boolean registerFragmentNode(RouterFragmentModel routerFragmentModel) {
//        if (routerFragmentModel == null) {
//            return false;
//        }
//        if (routerFragmentModel.isLazy()) {
//            if (routerFragmentLazyModelSet.contains(routerFragmentModel)) {
//                return false;
//            }
//            routerFragmentLazyModelSet.add(routerFragmentModel);
//        } else {
//            addFragmentMapping(routerFragmentModel);
//        }

        return true;
    }

//    private Class addFragmentMapping(RouterFragmentModel routerFragmentModel) {
//        try {
//            Fragment fragment = (Fragment) Class.forName(routerFragmentModel.getPath()).newInstance();
//            Class<? extends Fragment> fragmentCls = fragment.getClass();
//            fragmentClsManagers.put(routerFragmentModel.getSchema(), fragmentCls);
//            AbsRouterMap.addFragmentRouter(routerFragmentModel.getSchema(), fragmentCls);
//            return fragmentCls;
////            fragmentClsManagers.put(routerFragmentModel.getSchema(), fragmentCls);
//        } catch (ClassNotFoundException e) {
//            VivaLog.e(TAG, "ClassNotFoundException :" + routerFragmentModel.getPath(), e);
//        } catch (Throwable e) {
//            VivaLog.e(TAG, "Throwable", e);
//        }
//        return null;
//    }
}
