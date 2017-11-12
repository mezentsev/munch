package com.munch.suggest.model;

import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface YaSuggestApi {
    @GET("suggest-endings")
    Call<List<Suggest>> getSuggestsList(@Query("part") @Nullable String query);
}
