package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.munch.suggest.data.SuggestResponse;

import java.util.Collections;

import io.reactivex.Single;

/**
 * Simple interactor that creates Navifation Suggest from query.
 */
public final class QueryInteractor implements SuggestInteractor {
    private final static String TAG = "[MNCH:QueryInteractor]";

    /**
     * Create one Navigation or Text Suggest in lower case based on Query.
     *
     * @param specification request specification
     * @return observable suggest response
     */
    @Override
    public Single<SuggestResponse> getSuggests(@NonNull RequestSpecification specification) {
        String query = specification.getQuery();

        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            String lowerQuery = query.toLowerCase().trim();
            Suggest querySuggest;

            if (Patterns.WEB_URL.matcher(lowerQuery).matches()) {
                Log.d(TAG, "Created navigation suggest for: " + lowerQuery);
                querySuggest = SuggestFactory.createNavigationSuggest(
                        lowerQuery,
                        Uri.parse(lowerQuery));
            } else {
                Log.d(TAG, "Created text suggest for: " + lowerQuery);
                querySuggest = SuggestFactory.createTextSuggest(lowerQuery);
            }

            return Single.just(
                    new SuggestResponse(
                            lowerQuery,
                            lowerQuery,
                            null,
                            Collections.singletonList(querySuggest)
                    )
            );
        }

        return Single.error(new IllegalStateException("Can't create query suggest"));
    }

    @NonNull
    @Override
    public RequestSpecification.Factory getSpecificationFactory() {
        return new RequestSpecification.Simple.Factory();
    }
}