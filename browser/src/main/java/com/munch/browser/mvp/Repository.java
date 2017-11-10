package com.munch.browser.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface Repository<T> {
    void add(@NonNull T item);

    void update(@NonNull T item);

    void remove(@NonNull T item);

    @Nullable
    List<T> query(@NonNull Specification specification);
}
