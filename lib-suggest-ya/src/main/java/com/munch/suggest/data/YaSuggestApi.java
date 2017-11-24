package com.munch.suggest.data;

import android.support.annotation.Nullable;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YaSuggestApi {
    // TODO: 17.11.17 make YaSpecification extends Specification
    @GET("suggest-endings?fact=1&mob=1&uil=ru&full_text_count=10")
    Single<SuggestResponse> get(@Query("part") @Nullable String query);
}
