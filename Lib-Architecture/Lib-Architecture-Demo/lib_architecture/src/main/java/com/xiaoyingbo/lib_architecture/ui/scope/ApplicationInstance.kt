package com.xiaoyingbo.lib_architecture.ui.scope;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**app单例，单例是依附于进程的，也就是说ApplicationInstance产生的ViewModelStore是对于整个进程的,<br/>
 * 也就是说app不死，这个产生的viewModel就持续有效*/
public class ApplicationInstance implements ViewModelStoreOwner {
    private final static ApplicationInstance sInstance = new ApplicationInstance();
    private ViewModelStore mAppViewModelStore;

    private ApplicationInstance() {
    }

    public static ApplicationInstance getInstance() {
        return sInstance;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        if (mAppViewModelStore == null)
            mAppViewModelStore = new ViewModelStore();
        return mAppViewModelStore;
    }
}