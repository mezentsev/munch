package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Simple suggest model.
 */
final class SuggestModel implements Suggest {
    @NonNull
    private final String mTitle;
    @Nullable
    private final Uri mUrl;
    @Nullable
    private final String mDescription;
    @Nullable
    private final SuggestType mType;
    private final double mWeight;

    SuggestModel(@NonNull String title,
                 @Nullable Uri url,
                 double weight,
                 @Nullable String description,
                 @Nullable SuggestType type) {
        mTitle = title;
        mUrl = url;
        mWeight = weight;
        mDescription = description;
        mType = type;
    }

    @NonNull
    @Override
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    @Override
    public SuggestType getType() {
        return mType;
    }

    @Nullable
    @Override
    public Uri getUrl() {
        return mUrl;
    }

    @Override
    public double getWeight() {
        return mWeight;
    }
}
