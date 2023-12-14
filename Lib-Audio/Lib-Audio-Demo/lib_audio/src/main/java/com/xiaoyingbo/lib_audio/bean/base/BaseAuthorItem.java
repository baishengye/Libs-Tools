package com.xiaoyingbo.lib_audio.bean.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**作者:某个事物的创建者*/
public class BaseAuthorItem implements Serializable {

    private String name;

    public BaseAuthorItem(String name) {
        this.name = name;
    }

    public BaseAuthorItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
