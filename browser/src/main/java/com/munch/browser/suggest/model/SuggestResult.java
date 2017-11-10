package com.munch.browser.suggest.model;

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
}
