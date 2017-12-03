package com.munch.suggest.model;

import android.support.annotation.NonNull;

import com.munch.suggest.data.SuggestResponse;

import io.reactivex.Single;

public interface SuggestInteractor {
    /**
     * Get list of suggests.
     *
     * @param specification suggest request specification
     * @return list of suggests
     */
    @NonNull
    Single<SuggestResponse> getSuggests(@NonNull RequestSpecification specification);

    /**
     * Get request specification factory.
     *
     * @return factory
     */
    @NonNull
    RequestSpecification.Factory getSpecificationFactory();

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
