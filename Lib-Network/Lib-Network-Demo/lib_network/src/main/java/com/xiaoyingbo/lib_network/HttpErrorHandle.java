package com.xiaoyingbo.lib_network;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

public class HttpErrorHandle<T> implements Function<Throwable, Single<T>> {
    @Override
    public Single<T> apply(Throwable throwable) {
        return Single.error(ExceptionHandle.handleException(throwable));
    }
}
