package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;

/**
 * Factory for build suggest models.
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

    @NonNull
    public static Suggest createSuggest(@NonNull String title) {
        Suggest suggest;

        if (Patterns.WEB_URL.matcher(title).matches()) {
            suggest = SuggestFactory.createNavigationSuggest(
                    title,
                    Uri.parse(title));
        } else {
            suggest = SuggestFactory.createTextSuggest(title);
        }

        return suggest;
    }
}
