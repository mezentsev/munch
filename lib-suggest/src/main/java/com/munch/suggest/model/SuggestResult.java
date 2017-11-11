package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;

public interface SuggestResult {
    @NonNull
    String getText();

    @NonNull
    Uri getUrl();

    class FullSuggestResult implements SuggestResult {

        @NonNull
        private final String mText;
        @NonNull
        private final Uri mUrl;

        FullSuggestResult(@NonNull String text,
                          @NonNull Uri url) {

            mText = text;
            mUrl = url;
        }

        @NonNull
        @Override
        public String getText() {
            return mText;
        }

        @NonNull
        @Override
        public Uri getUrl() {
            return mUrl;
        }
    }

    class FactSuggestResult extends FullSuggestResult {

        @NonNull
        private String mDescription;

        FactSuggestResult(@NonNull String text,
                          @NonNull Uri url,
                          @NonNull String description) {
            super(text, url);
            mDescription = description;
        }

        @NonNull
        public String getDescription() {
            return mDescription;
        }
    }
}
