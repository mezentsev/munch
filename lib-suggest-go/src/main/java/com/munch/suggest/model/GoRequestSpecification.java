package com.munch.suggest.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

final class GoRequestSpecification extends RequestSpecification.Simple {
    private final int Fact = 1;
    private final int Mob = 1;

    public GoRequestSpecification(@Nullable String query,
                                  @IntRange(from = 0) int max,
                                  @Nullable String lang) {
        super(query, max, lang);
    }

    @NonNull
    @Override
    public Map<String, String> build() {
        Map<String, String> specs = new HashMap<>(5);
        specs.put("q", Query);
        specs.put("hl", Lang);
        specs.put("client", "chrome");
        return specs;
    }

    static class Factory implements RequestSpecification.Factory {

        @NonNull
        @Override
        public RequestSpecification get(@Nullable String query,
                                        @IntRange(from = 0) int max,
                                        @Nullable String lang) {
            return new GoRequestSpecification(query, max, lang);
        }
    }
}
