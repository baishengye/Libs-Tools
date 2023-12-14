package com.xiaoyingbo.lib_util.BSY.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.xiaoyingbo.lib_util.thirdparty.Utils;

public class BSYUtils {
    /**初始化app
     * @param context 上下文*/
    public static void init(Context context) {
        Utils.init(context);
    }

    /**初始化app
     * @param app app引用*/
    public static void init(final Application app) {
        Utils.init(app);
    }

    /**获取app*/
    public static Application getApp() {
        return Utils.getApp();
    }

    /**判断是否debug模式*/
    public static boolean isDebug(){
        return getApp().getApplicationContext().getApplicationInfo()!=null&&((getApp().getApplicationContext().getApplicationInfo().flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0);
    }
}
