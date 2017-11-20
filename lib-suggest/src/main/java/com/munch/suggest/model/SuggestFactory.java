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
                                             double weight) {
        return new SuggestModel(
                title,
                null,
                weight,
                null,
                Suggest.SuggestType.TEXT);
    }

    public static Suggest createTextSuggest(@NonNull String title) {
        return createTextSuggest(title, 1.);
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
                                            @Nullable String description) {
        return new SuggestModel(
                title,
                null,
                0.,
                description,
                Suggest.SuggestType.FACT);
    }
}
