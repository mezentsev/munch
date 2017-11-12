package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;

public interface Suggest {
    @NonNull
    String getText();

    @NonNull
    Uri getUrl();

    class FullSuggest implements Suggest {

        @NonNull
        private final String mText;
        @NonNull
        private final Uri mUrl;

        FullSuggest(@NonNull String text,
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

    class FactSuggest extends FullSuggest {

        @NonNull
        private String mDescription;

        FactSuggest(@NonNull String text,
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
