package com.munch.suggest.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.helpers.Lazy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class YaSuggestInteractor implements SuggestInteractor {
    private static final String BASE_URL = "http://yandex.ru/";

    @NonNull
    private final YaSuggestApi mYaSuggestApi;

    YaSuggestInteractor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mYaSuggestApi = retrofit.create(YaSuggestApi.class);
    }

    @NonNull
    @Override
    public Call<List<Suggest>> getSuggests(@Nullable String query) {
        return mYaSuggestApi.getSuggestsList(query);
    }

    public static class LazyImpl implements Lazy<SuggestInteractor> {
        @Nullable
        private static YaSuggestInteractor mSuggestInteractor;

        public LazyImpl() {
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
