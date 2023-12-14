package com.baishengye.lib_bluetooth.bt.interfaces;

public interface BTStateCallback {
    default void onBTEnableChange(boolean isEnable){}
}
