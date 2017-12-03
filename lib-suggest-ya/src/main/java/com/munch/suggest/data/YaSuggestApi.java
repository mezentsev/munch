package com.munch.suggest.data;

import android.support.annotation.NonNull;

import com.munch.suggest.model.RequestSpecification;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface YaSuggestApi {
    /**
     * {@link RequestSpecification}
     *
     * @param queryMap
     * @return
     */
    @GET("suggest-endings")
    Single<SuggestResponse> get(@QueryMap @NonNull Map<String, String> queryMap);
}
