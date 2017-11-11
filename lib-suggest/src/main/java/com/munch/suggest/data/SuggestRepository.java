package com.munch.suggest.data;

import android.support.annotation.NonNull;

import com.munch.mvp.Repository;
import com.munch.suggest.model.SuggestResult;

public abstract class SuggestRepository implements Repository<SuggestResult> {
    @Override
    public void add(@NonNull SuggestResult item) {
    }

    @Override
    public void update(@NonNull SuggestResult item) {
    }

    @Override
    public void remove(@NonNull SuggestResult item) {
    }
}
