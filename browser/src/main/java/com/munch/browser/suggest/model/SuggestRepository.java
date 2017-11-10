package com.munch.browser.suggest.model;

import android.support.annotation.NonNull;

import com.munch.browser.mvp.Repository;

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
