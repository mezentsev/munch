package com.munch.suggest.data;

import android.support.annotation.NonNull;

import com.munch.suggest.model.Suggest;

public interface SuggestClicklistener {
    void onSuggestClicked(@NonNull Suggest suggest);
}
