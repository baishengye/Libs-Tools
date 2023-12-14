package com.xiaoyingbo.lib_architecture.data.response;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({ResultSource.NETWORK,ResultSource.DATABASE,ResultSource.LOCAL_FILE,ResultSource.SHARE_PREFERENCE,ResultSource.MMKV,ResultSource.BLUETOOTH,ResultSource.OTHER})
public @interface ResultSource {
    int NETWORK=0x1;
    int DATABASE=0x2;
    int LOCAL_FILE=0x3;
    int MMKV = 0x4;
    int SHARE_PREFERENCE = 0x5;
    int BLUETOOTH = 0x06;
    int OTHER = 0x07;
}
