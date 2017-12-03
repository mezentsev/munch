package com.munch.suggest.data;

import android.support.annotation.NonNull;

import com.munch.suggest.model.Suggest;

/**
 * Click listener interface.
 */
public interface SuggestClicklistener {
    void onSuggestClicked(@NonNull Suggest suggest);
}
