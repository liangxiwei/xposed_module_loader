package com.wind.xposed.entry.hooker;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author liangxiwei
 * @version 1.0
 * @since 2020/10/17
 */
public class IntentHooker implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Class intentClass = lpparam.classLoader.loadClass("android.content.Intent");
        XposedBridge.hookAllMethods(intentClass, "setClassName", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (param.args[1] != null && param.args[1] instanceof String) {
                    String replace = ((String) param.args[1]).replace("com.tencent.mm.duokai", "com.tencent.mm");
                    param.args[1] = replace;
                }
            }
        });
    }
}
