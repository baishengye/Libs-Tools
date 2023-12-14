package com.xiaoyingbo.lib_util.BSY.util;

import android.text.TextUtils;
import android.util.Log;

/**Log的工具类*/
public class BSYLogUtils {

    /**debug的日志*/
    public static void d(boolean isDebug,String tag,String msg,Throwable t){
        if(!isDebug) return;
        if(null==t){
            Log.d(tag,msg);
        }else{
            Log.d(tag,msg,t);
        }
    }

    /**debug的日志*/
    public static void d(String tag,String msg,Throwable t){
        d(true,tag,msg,t);
    }

    /**debug的日志*/
    public static void d(String tag,String msg){
        d(tag,msg,null);
    }

    /**Verbose的日志*/
    public static void v(boolean isDebug,String tag,String msg,Throwable t){
        if(!isDebug) return;
        if(null==t){
            Log.v(tag,msg);
        }else {
            Log.v(tag, msg, t);
        }
    }

    /**info的日志*/
    public static void i(boolean isDebug,String tag,String msg,Throwable t){
        if(!isDebug) return;
        if(null==t){
            Log.i(tag,msg);
        }else {
            Log.i(tag, msg, t);
        }
    }

    /**warn的日志*/
    public static void w(boolean isDebug,String tag,String msg,Throwable t){
        if(!isDebug) return;
        if(null==t){
            Log.w(tag,msg);
        }else {
            Log.w(tag, msg, t);
        }
    }

    /**error的日志*/
    public static void e(boolean isDebug,String tag,String msg,Throwable t){
        if(!isDebug) return;
        if(null==t){
            Log.e(tag,msg);
        }else {
            Log.e(tag, msg, t);
        }
    }

    /**asset的日志:错误严重到可能会把程序崩溃*/
    public static void wtf(boolean isDebug,String tag,String msg,Throwable t){
        if(!isDebug) return;
        if(null==t){
            Log.wtf(tag,msg);
        }else if(TextUtils.isEmpty(msg)){
            Log.wtf(tag, t);
        }else {
            Log.wtf(tag, msg,t);
        }
    }
}
