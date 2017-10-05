package com.munch.browser.mvp;

import android.support.annotation.NonNull;

public interface IPresenter<V> {
    void attach(@NonNull V view);
    void detach();
}
