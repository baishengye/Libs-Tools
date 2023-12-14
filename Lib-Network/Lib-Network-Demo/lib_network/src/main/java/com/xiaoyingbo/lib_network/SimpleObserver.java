package com.xiaoyingbo.lib_network;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;


public abstract class SimpleObserver<T> implements SingleObserver<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onSuccess(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    protected abstract void onFailed(ExceptionHandle.ResponseThrowable errorMsg);

    protected void onFailure(Throwable e) {
        if (e instanceof ExceptionHandle.ResponseThrowable) {
            ExceptionHandle.ResponseThrowable throwable = (ExceptionHandle.ResponseThrowable) e;
            onFailed(throwable);
        } else {
            e.printStackTrace();
        }
    }
}
