package xiyou.mc.framework.parse;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import xiyou.mc.framework.tree.node.BundleServiceElementImpl;
import xiyou.mc.framework.tree.node.api.BundleServiceElement;
import xiyou.mc.framework.tree.node.entity.BundleMapModel;
import xiyou.mc.framework.tree.node.entity.RouterModuleServiceModel;
import xiyou.mc.framework.tree.root.BundleRootImpl;
import xiyou.mc.framework.tree.root.api.BundleRoot;
import xiyou.mc.framework.tree.session.BundleSessionImpl;
import xiyou.mc.framework.tree.session.api.BundleSession;

/**
 * Created by xiyouMc on 01/11/2017.
 */

public class XmlParseHandler extends DefaultHandler {

    private static final String TAG = "XmlParseHandler";

    private BundleRoot bundleRoot = new BundleRootImpl();

    private BundleSession bundleSession;

    @Override
    public void startDocument() throws SAXException {

    }

    public BundleRoot getBundleRoot() {
        return bundleRoot;
    }

    private RouterModuleServiceModel mRouterModuleServiceModel;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        Log.d(TAG, "qName:" + qName);
        if (qName.equals("Bundle")) {

            BundleMapModel mBundleMapModel = new BundleMapModel();
            mBundleMapModel.setRouterMapClassPath(attributes.getValue("name"));
            String lazy = attributes.getValue("lazy");
            if (lazy == null || lazy.isEmpty()) {
                lazy = "true";
            }
            mBundleMapModel.setLazy(Boolean.parseBoolean(lazy));

            bundleSession = new BundleSessionImpl(mBundleMapModel);
            bundleRoot.addChild(bundleSession);
        } else if (qName.equals("Service")) {
            mRouterModuleServiceModel = new RouterModuleServiceModel();
            String api = attributes.getValue("api");
            String impl = attributes.getValue("impl");
            mRouterModuleServiceModel.setApi(api);
            mRouterModuleServiceModel.setImpl(impl);
            String lazy = attributes.getValue("lazy");
            if (lazy == null || lazy.isEmpty()) {
                lazy = "true";
            }
            String preLoad = attributes.getValue("preLoad");
            if (preLoad == null || preLoad.isEmpty()) {
                preLoad = "true";
            }
            mRouterModuleServiceModel.setPreLoad(Boolean.parseBoolean(preLoad));
            mRouterModuleServiceModel.setLazy(Boolean.parseBoolean(lazy));
            Log.d(TAG, "Service " + attributes.getValue("api") + " " + attributes.getValue("impl"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equals("Service")) {
            BundleServiceElement bundleServiceElement = new BundleServiceElementImpl();
            bundleServiceElement.addServiceData(mRouterModuleServiceModel);
            bundleSession.addService(bundleServiceElement);
        } else if (qName.equals("Bundle")) {
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
