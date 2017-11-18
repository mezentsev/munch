package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Factory for Suggests.
 * <p>
 * {@link com.munch.suggest.model.Suggest.SuggestType}
 */
public final class SuggestFactory {
    @NonNull
    public static Suggest createTextSuggest(@NonNull String title,
                                            @NonNull Uri url,
                                            double weight) {
        return new SuggestModel(
                title,
                url,
                weight,
                null,
                Suggest.SuggestType.TEXT);
    }

    @NonNull
    public static Suggest createNavigationSuggest(@NonNull String title,
                                                  @NonNull Uri url) {
        return new SuggestModel(
                title,
                url,
                0.,
                null,
                Suggest.SuggestType.NAV);
    }

    @NonNull
    public static Suggest createFactSuggest(@NonNull String title,
                                            @NonNull Uri url,
                                            @Nullable String description) {
        return new SuggestModel(
                title,
                url,
                0.,
                description,
                Suggest.SuggestType.FACT);
    }
}
