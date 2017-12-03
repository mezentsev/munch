package com.munch.suggest.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.mvp.Specification;

import java.util.HashMap;
import java.util.Map;

/**
 * Specification for http request.
 * <p>
 * {@see <a href="https://futurestud.io/tutorials/retrofit-2-add-multiple-query-parameter-with-querymap">Retrofit 2</a>}
 */
public interface RequestSpecification extends Specification {
    @Nullable
    String getQuery();

    /**
     * Build query specification.
     *
     * @return QueryMap
     */
    @NonNull
    Map<String, String> build();

    /**
     * Factory for request specification.
     */
    interface Factory {
        @NonNull
        RequestSpecification get(@Nullable String query,
                                 @IntRange(from = 0) int max,
                                 @Nullable String lang);
    }

    /**
     * Simple implementation RequestSpecs.
     */
    class Simple implements RequestSpecification {

        @Nullable
        protected final String Query;
        protected final int Max;
        @Nullable
        protected final String Lang;

        public Simple(@Nullable String query,
                      @IntRange(from = 0) int max,
                      @Nullable String lang) {
            Query = query;
            Max = max;
            Lang = lang;
        }

        @Nullable
        @Override
        public String getQuery() {
            return Query;
        }

        @NonNull
        @Override
        public Map<String, String> build() {
            Map<String, String> specs = new HashMap<>(3);
            specs.put("query", Query);
            specs.put("count", String.valueOf(Max));
            specs.put("lang_id", Lang);
            return specs;
        }

        public static class Factory implements RequestSpecification.Factory {

            @NonNull
            @Override
            public RequestSpecification get(@Nullable String query,
                                            int max,
                                            @Nullable String lang) {
                return new Simple(query, max, lang);
            }
        }
    }
}