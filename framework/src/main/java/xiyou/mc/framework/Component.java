package xiyou.mc.framework;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

public abstract class Component {
    protected static final String TAG = "AdvanceRouterMapXMLV2";


    public abstract void onCreate();


    /**
     * 反射 获取 BuildConfig 的参数 用于在子 module 使用
     *
     * @param context   上下文
     * @param fieldName 参数名
     * @return 返回 反射得到的类
     */
    protected Object getBuildConfigValue(Context context, String fieldName) {
        try {
            Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "getBuildConfigValue ClassNotFoundException", e);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "getBuildConfigValue NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getBuildConfigValue IllegalAccessException", e);
        }
        return null;
    }
}
