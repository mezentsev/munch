package com.munch.mvp;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

public interface FlowableRepository<T> {
    Flowable<List<T>> get();

    void save(@NonNull T item);

    void remove(@NonNull T item);
}
