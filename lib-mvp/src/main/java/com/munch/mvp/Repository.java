package com.munch.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface Repository<T> {
    List<T> get();

    void save(@NonNull T item);

    void remove(@NonNull T item);
}
