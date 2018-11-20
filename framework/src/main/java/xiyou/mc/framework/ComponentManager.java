package xiyou.mc.framework;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import xiyou.mc.framework.parse.XmlParseHandler;
import xiyou.mc.framework.tree.root.api.BundleRoot;

public class ComponentManager {
    private static final String TAG = "ComponentManager";
    private static boolean isInit = false;
    private static BundleRoot bundleRoot;

    public static synchronized void initBundleTree(ComponentManagerConfig config) {
        if (isInit) {
            return;
        }
        long startTime = System.currentTimeMillis();
        InputStream is = null;
        try {
            is = config.context.getAssets().open("bundles.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParseHandler xmlParseHandler = new XmlParseHandler();
            parser.parse(is, xmlParseHandler);
            is.close();
            bundleRoot = xmlParseHandler.getBundleRoot();
            //init onCreate
            bundleRoot.initNoLazyRootMap();
            isInit = true;
        } catch (Exception e) {
            Log.e(TAG, "Exception!!", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        Log.d(TAG, "initBundleTree timeStamp:" + (System.currentTimeMillis() - startTime));
    }

    private static BundleRoot getBundleRoot() {
        if (bundleRoot == null) {
            throw new RuntimeException("need call initBundleTree()");
        }
        return bundleRoot;
    }

    public static <T> T getService(Class<T> cls) {
        return (T) getBundleRoot().findService(cls.getName());
    }

//    public static Class<? extends Fragment> findFragment(String scheme) {
//        return Component.getBundleRoot().findLazyFragment(scheme);
//    }

    public static void removeService(String serviceApi) {
        getBundleRoot().removeService(serviceApi);
    }
}
