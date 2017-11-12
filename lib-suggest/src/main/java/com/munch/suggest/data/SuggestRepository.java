package com.munch.suggest.data;

import android.support.annotation.NonNull;

import com.munch.mvp.Repository;
import com.munch.suggest.model.Suggest;

public abstract class SuggestRepository implements Repository<Suggest> {
    @Override
    public void add(@NonNull Suggest item) {
    }

    @Override
    public void update(@NonNull Suggest item) {
    }

    @Override
    public void remove(@NonNull Suggest item) {
    }
}
