package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;

import com.munch.suggest.data.SuggestResponse;

import java.util.Collections;

import io.reactivex.Observable;

/**
 * Simple interactor that creates Navifation Suggest from query.
 */
public final class QueryInteractor implements SuggestInteractor {
    private final static String TAG = QueryInteractor.class.getSimpleName();

    @Override
    public Observable<SuggestResponse> getSuggests(@Nullable String query) {
        if (query != null ) {
            String lowerQuery = query.toLowerCase();

            if (Patterns.WEB_URL.matcher(lowerQuery).matches()) {
                Log.d(TAG, "Created navigation suggest for: " + lowerQuery);
                return Observable.just(
                        new SuggestResponse(
                                lowerQuery,
                                lowerQuery,
                                null,
                                Collections.singletonList(
                                        SuggestFactory.createNavigationSuggest(lowerQuery, Uri.parse(lowerQuery))
                                )
                        )
                );
            } else {
                Log.d(TAG, "Created text suggest for: " + lowerQuery);
                return Observable.just(
                        new SuggestResponse(
                                lowerQuery,
                                lowerQuery,
                                null,
                                Collections.singletonList(
                                        SuggestFactory.createTextSuggest(lowerQuery)
                                )
                        )
                );
            }
        }

        return Observable.error(new IllegalStateException("Can't create query suggest"));
    }
}