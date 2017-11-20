package com.munch.suggest.data;

import android.support.annotation.Nullable;

import com.munch.suggest.model.Suggest;

import java.util.List;

/**
 * Response from suggest provider.
 */
public final class SuggestResponse {
    @Nullable
    private final String mQuery;
    @Nullable
    private final String mCandidate;
    @Nullable
    private final String mSearchBaseUrl;
    @Nullable
    private final List<Suggest> mSuggests;

    public SuggestResponse(@Nullable String query,
                           @Nullable String candidate,
                           @Nullable String searchBaseUrl,
                           @Nullable List<Suggest> suggests) {

        mQuery = query;
        mCandidate = candidate;
        mSearchBaseUrl = searchBaseUrl;
        mSuggests = suggests;
    }

    /**
     * Get user query.
     *
     * @return query
     */
    @Nullable
    public String getQuery() {
        return mQuery;
    }

    /**
     * Get best suggest for user query.
     *
     * @return candidate
     */
    @Nullable
    public String getCandidate() {
        return mCandidate;
    }

    /**
     * Get base search url.
     *
     * @return search url
     */
    @Nullable
    public String getSearchBaseUrl() {
        return mSearchBaseUrl;
    }

    /**
     * Get list of suggests.
     *
     * @return list
     */
    @Nullable
    public List<Suggest> getSuggests() {
        return mSuggests;
    }

    public static SuggestResponse empty() {
        return new SuggestResponse(null, null, null, null);
    }
}
