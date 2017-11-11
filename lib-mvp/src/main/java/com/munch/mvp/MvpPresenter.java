package com.munch.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface MvpPresenter<V extends MvpView> {
    void onCreate();

    void onFirstAttachView();

    void attachView(@NonNull V view);

    void detachView();

    void onDestroy();

    abstract class Simple<V extends MvpView> implements MvpPresenter<V> {
        @Nullable
        protected V mView;

        @Override
        @CallSuper
        public void attachView(@NonNull V view) {
            mView = view;
        }

        @Override
        @CallSuper
        public void detachView() {
            mView = null;
        }
    }
}
