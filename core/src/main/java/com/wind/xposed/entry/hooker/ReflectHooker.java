package com.wind.xposed.entry.hooker;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author liangxiwei
 * @since 2020/10/16
 */
public class ReflectHooker implements IXposedHookLoadPackage {
    private static final String TAG = "ReflectHooker";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.hookAllMethods(Class.class, "forName", new XC_MethodHook(){
            @Override
            public void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if(param.args[0] != null && ((String)param.args[0]).startsWith("com.tencent.mm.duokai")){
                    String replace = ((String) param.args[0]).replace("com.tencent.mm.duokai", "com.tencent.mm");
                    Log.i(TAG, replace);
                }
                super.beforeHookedMethod(param);
            }
        });
    }
}
