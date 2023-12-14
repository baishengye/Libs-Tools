package com.xiaoyingbo.lib_architecture.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**防抖动状态类
 * ObservableField是只有数据发生变化的时候才会触发
 */
public class State <T> extends ObservableField<T> {
    /**是否消除抖动*/
    private final boolean mIsDebouncing;

    public State(@NonNull T value){
        this(value,false);
    }

    public State(@NonNull T value,boolean isDebouncing){
        super(value);
        mIsDebouncing=isDebouncing;
    }

    @Override
    public void set(T value) {
        boolean isUnchanged=get()==value;
        super.set(value);
        if(!mIsDebouncing&&isUnchanged){
            notifyChange();
        }
    }
}
