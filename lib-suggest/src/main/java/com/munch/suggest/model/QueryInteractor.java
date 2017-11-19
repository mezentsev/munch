package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Patterns;

import com.munch.suggest.data.SuggestResponse;

import java.util.Collections;

import io.reactivex.Observable;

/**
 * Simple interactor that creates Navifation Suggest from query.
 */
public final class QueryInteractor implements SuggestInteractor {
    @Override
    public Observable<SuggestResponse> getSuggests(@Nullable String query) {
        if (query != null && Patterns.WEB_URL.matcher(query).matches()) {
            return Observable.just(
                    new SuggestResponse(
                            query,
                            query,
                            Collections.singletonList(
                                    SuggestFactory.createNavigationSuggest(query, Uri.parse(query))
                            )
                    )
            );
        }

        return Observable.error(new IllegalStateException("Can't create query suggest"));
    }
}