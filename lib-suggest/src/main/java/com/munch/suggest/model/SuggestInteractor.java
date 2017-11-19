package com.munch.suggest.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.suggest.data.SuggestResponse;

import io.reactivex.Observable;

public interface SuggestInteractor {
    /**
     * Get list of suggests.
     *
     * @param query user query
     * @return list of suggests
     */
    Observable<SuggestResponse> getSuggests(@Nullable String query);

    interface Factory {
        /**
         * Create instance of SuggestInteractor.
         *
         * @return SuggestInteractor
         */
        @NonNull
        SuggestInteractor get();
    }
}
