package com.wind.xposed.entry.hooker;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author liangxiwei
 * @since 2020/10/16
 */
public class ReflectHooker implements IXposedHookLoadPackage {
    private static final String TAG = "ReflectHooker";
    private ClassLoader classLoader = null;

    public ReflectHooker() {
        classLoader = getClass().getClassLoader();
        Log.i("lxw", classLoader.hashCode() + " ReflectHooker classLoader " + classLoader.toString());
        try {
            Class aClazz = classLoader.loadClass("com.google.firebase.a");
            XposedHelpers.setStaticObjectField(aClazz, "bJT", new ArrayList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.hookAllMethods(Class.class, "forName", new XC_MethodHook() {
            @Override
            public void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (param.args[0] != null && ((String) param.args[0]).startsWith("com.tencent.mm.duokai")) {
                    Log.i(TAG, "before " + param.args[0]);
                    String replace = ((String) param.args[0]).replace("com.tencent.mm.duokai", "com.tencent.mm");
                    param.args[0] = replace;
                    Log.i(TAG, "after " + replace);
                    try {
                        Class<?> loadClass = getClass().getClassLoader().loadClass((String) param.args[0]);
                        param.setResult(loadClass);
                    } catch (Exception e) {
                        Log.i(TAG, "not found class ", e);
                    }
                } else {
                    try {
                        Class<?> loadClass = getClass().getClassLoader().loadClass((String) param.args[0]);
                        param.setResult(loadClass);
                    } catch (Exception e) {
                        Log.i(TAG, "not found class2 ", e);
                    }
                }
                super.beforeHookedMethod(param);
            }
        });
    }
}
