package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FullSuggest implements Suggest {
    @NonNull
    private final String mTitle;
    @NonNull
    private final Uri mUrl;
    @Nullable
    private final String mDescription;
    @Nullable
    private final SuggestType mType;
    private final double mWeight;

    FullSuggest(@NonNull String title,
                @NonNull Uri url,
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

    @NonNull
    @Override
    public Uri getUrl() {
        return mUrl;
    }

    @Override
    public double getWeight() {
        return mWeight;
    }
}
