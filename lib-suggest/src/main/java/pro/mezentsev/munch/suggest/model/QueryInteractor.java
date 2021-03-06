package pro.mezentsev.munch.suggest.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import pro.mezentsev.munch.suggest.data.SuggestResponse;

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
            Suggest querySuggest = SuggestFactory.createSuggest(lowerQuery);

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