package com.munch.suggest.data;

import android.support.annotation.Nullable;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YaSuggestApi {
    // TODO: 17.11.17 make YaSpecification extends Specification
    @GET("suggest-endings?fact=1&mob=1&uil=en&full_text_count=5")
    Observable<SuggestResponse> get(@Query("part") @Nullable String query);
}
