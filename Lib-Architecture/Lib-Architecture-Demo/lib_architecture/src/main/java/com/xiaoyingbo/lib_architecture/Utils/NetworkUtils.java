package com.xiaoyingbo.lib_architecture.Utils;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresPermission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**网络工具类*/
public final class NetworkUtils {

    /**获得详细的网络状态
     * @return
     * {@code NetworkState.NONE}:没有网络<br>
     * {@code NetworkState.WIFI}:Wifi网络<br>
     * {@code NetworkState.MOBILE}:移动网络<br>
     * {@code NetworkState.MOBILE_2G}:2G网络<br>
     * {@code NetworkState.MOBILE_3G}:3G网络<br>
     * {@code NetworkState.MOBILE_4G}:4G网络<br>
     * {@code NetworkState.MOBILE_5G}:5G网络<br>*/
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static int getDetailedNetworkState() {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) AppUtils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        //如果当前没有网络
        if (null == connManager)
            return BSYNetworkState.NONE;
        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return BSYNetworkState.NONE;
        }

        // 判断是不是连接的是不是wifi
         NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return BSYNetworkState.WIFI;
                }
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return BSYNetworkState.MOBILE_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return BSYNetworkState.MOBILE_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return BSYNetworkState.MOBILE_4G;
                        case TelephonyManager.NETWORK_TYPE_NR: //对应的20 只有依赖为android 10.0才有此属性
                            return BSYNetworkState.MOBILE_5G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return BSYNetworkState.MOBILE_3G;
                            } else {
                                return BSYNetworkState.MOBILE;
                            }
                    }
                }
        }
        return BSYNetworkState.NONE;
    }

    /**获得简单的网络状态
     * @return
     * {@code NetworkState.NONE}:没有网络<br>
     * {@code NetworkState.WIFI}:Wifi网络<br>
     * {@code NetworkState.MOBILE}:移动网络<br>*/
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static int getNetworkState() {
        @BSYNetworkState int networkState=getDetailedNetworkState();
        switch (networkState) {
            case BSYNetworkState.MOBILE:
            case BSYNetworkState.MOBILE_2G:
            case BSYNetworkState.MOBILE_3G:
            case BSYNetworkState.MOBILE_4G:
            case BSYNetworkState.MOBILE_5G:
                return BSYNetworkState.MOBILE;
            case BSYNetworkState.NONE:
                return BSYNetworkState.NONE;
            case BSYNetworkState.WIFI:
                return BSYNetworkState.WIFI;
        }
        return BSYNetworkState.NONE;
    }

    /**
     * 判断网络是否连接
     * @return 网络是否连接
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isConnected() {
        return getNetworkState() != BSYNetworkState.NONE;
    }

    /**网络状态*/
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BSYNetworkState.NONE, BSYNetworkState.MOBILE, BSYNetworkState.WIFI,
            BSYNetworkState.MOBILE_2G, BSYNetworkState.MOBILE_3G, BSYNetworkState.MOBILE_4G, BSYNetworkState.MOBILE_5G})
    public @interface BSYNetworkState {
        /**无网络连接*/
        int NONE=-0x1;
        /**非移动,电信,联通运营商提供的移动信号,识别不出来是什么移动信号时*/
        int MOBILE=0x0;
        /**Wifi信号*/
        int WIFI=0x1;
        /**移动,电信,联通运营商提供的移动信号*/
        int MOBILE_2G=0x2;
        /**移动,电信,联通运营商提供的移动信号*/
        int MOBILE_3G=0x3;
        /**移动,电信,联通运营商提供的移动信号*/
        int MOBILE_4G=0x4;
        /**移动,电信,联通运营商提供的移动信号*/
        int MOBILE_5G=0x5;
    }
}
