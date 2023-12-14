package com.xiaoyingbo.lib_util.BSY.util;

import android.os.Process;

public class BSYProcessUtil {
    /**
     * 杀死当前应用
     */
    public static void killApp() {
        Process.killProcess(Process.myPid());
    }
}
