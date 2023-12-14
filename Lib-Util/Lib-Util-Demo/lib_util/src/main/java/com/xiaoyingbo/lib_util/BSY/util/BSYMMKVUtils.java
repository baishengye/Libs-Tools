package com.xiaoyingbo.lib_util.BSY.util;

import android.app.Application;
import android.content.Context;

import com.tencent.mmkv.MMKV;

public class BSYMMKVUtils {
    /**MMKV实例*/
    private static volatile MMKV mKv;

    /**
     * app启动时初始化MMKV:默认根路径
     * @param context 通过context初始化MMKV
     * @return MMKV根目录*/
    public static String init(Context context){
        String rootDir=MMKV.initialize(context);
        mKv = MMKV.defaultMMKV();
        return rootDir;
    }

    public static String init(Context context,String rootDir){
        String rootDirMMKV=MMKV.initialize(context,context.getFilesDir().getAbsolutePath() + rootDir);
        mKv = MMKV.defaultMMKV();
        return rootDirMMKV;
    }

    public static String init(Application application){
        String rootDir=MMKV.initialize(application);
        mKv = MMKV.defaultMMKV();
        return rootDir;
    }

    /**推入boolean值*/
    public static boolean putBoolean(String key,boolean value){
        return mKv.encode(key,value);
    }

    /**获取boolean值*/
    public static boolean getBoolean(String key,boolean defValue){
        return mKv.getBoolean(key,defValue);
    }


    /**推入String值*/
    public static boolean putString(String key,String value){
        return mKv.encode(key,value);
    }

    /**获取String值*/
    public static String getString(String key,String defValue){
        return mKv.getString(key,defValue);
    }
}
