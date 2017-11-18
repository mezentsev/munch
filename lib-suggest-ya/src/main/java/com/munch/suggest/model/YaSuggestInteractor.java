package com.munch.suggest.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.suggest.data.SuggestResponse;
import com.munch.suggest.data.YaSuggestApi;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class YaSuggestInteractor implements SuggestInteractor {
    private static final String BASE_URL = "http://yandex.ru/";

    @NonNull
    private final YaSuggestApi mYaSuggestApi;

    YaSuggestInteractor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new YaConverterFactory())
                .build();

        mYaSuggestApi = retrofit.create(YaSuggestApi.class);
    }

    @NonNull
    @Override
    public Observable<SuggestResponse> getSuggests(@Nullable String query) {
        return mYaSuggestApi.get(query);
    }

    public static class Factory implements SuggestInteractor.Factory {
        @Nullable
        private static YaSuggestInteractor mSuggestInteractor;

        public Factory() {
        }

        @NonNull
        @Override
        public YaSuggestInteractor get() {
            if (mSuggestInteractor == null) {
                mSuggestInteractor = new YaSuggestInteractor();
            }

            return mSuggestInteractor;
        }
    }
}
