package com.munch.suggest.model;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

final class YaRequestSpecification extends RequestSpecification.Simple {
    private final int Fact = 1;
    private final int Mob = 1;

    public YaRequestSpecification(@Nullable String query,
                                  @IntRange(from = 0) int max,
                                  @Nullable String lang) {
        super(query, max, lang);
    }

    @NonNull
    @Override
    public Map<String, String> build() {
        Map<String, String> specs = new HashMap<>(5);
        specs.put("part", Query);
        specs.put("full_text_count", String.valueOf(Max));
        specs.put("uil", Lang);
        specs.put("mob", String.valueOf(Mob));
        specs.put("fact", String.valueOf(Fact));
        return specs;
    }

    static class Factory implements RequestSpecification.Factory {

        @NonNull
        @Override
        public RequestSpecification get(@Nullable String query,
                                        @IntRange(from = 0) int max,
                                        @Nullable String lang) {
            return new YaRequestSpecification(query, max, lang);
        }
    }
}
