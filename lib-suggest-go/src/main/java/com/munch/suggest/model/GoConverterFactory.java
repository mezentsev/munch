package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.munch.suggest.data.SuggestResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class GoConverterFactory extends Converter.Factory {
    private static final String BASE_SEARCH_URL = "https://google.com/search?q=";
    private static final int PARSE_START_ID = 0;
    private static final int PARSE_NAV_FACT_STOP_ID = 5;

    private static final int PARSE_NAV_SIZE = 5;
    private static final int PARSE_NAV_TITLE_ID = 1;
    private static final int PARSE_NAV_URL_ID = 3;

    private static final int PARSE_FACT_SIZE = 3;
    private static final int PARSE_FACT_TITLE_ID = 1;
    private static final int PARSE_FACT_DESCRIPTION_ID = 2;

    private static final int PARSE_TEXT_TITLE_ID = 0;
    private static final int PARSE_TEXT_WEIGHT_ID = 1;

    @Override
    public Converter<ResponseBody, SuggestResponse> responseBodyConverter(@NonNull Type type,
                                                                          @NonNull Annotation[] annotations,
                                                                          @NonNull Retrofit retrofit) {
        return JsonConverter.INSTANCE;
    }

    final static class JsonConverter implements Converter<ResponseBody, SuggestResponse> {
        static final JsonConverter INSTANCE = new JsonConverter();
        private static final String TAG = "[MNCH:GoConverter]";

        private JsonConverter() {

        }

        @Override
        public SuggestResponse convert(@NonNull ResponseBody responseBody) throws IOException {
            try {
                JsonArray jsonArray = new Gson().fromJson(responseBody.string(), JsonArray.class);
                List<Suggest> suggests = new ArrayList<>();
                int suggestionId = PARSE_START_ID;

                String query = jsonArray.get(suggestionId++).getAsString();

                JsonElement jsonElement = jsonArray.get(suggestionId);
                if (jsonElement.isJsonArray()) {

                    JsonArray array = jsonElement.getAsJsonArray();
                    for (int i = 0; i < array.size(); ++i) {
                        Suggest addSuggest;
                        String title = array.get(i).getAsString();

                        if (Patterns.WEB_URL.matcher(title).matches()) {
                            addSuggest = SuggestFactory.createNavigationSuggest(
                                    title,
                                    Uri.parse(title));
                            Log.d(TAG, "Created navigation suggest for: " + title);
                        } else {
                            addSuggest = SuggestFactory.createTextSuggest(
                                    title,
                                    0.
                            );
                            Log.d(TAG, "Created text suggest for: " + title);
                        }

                        suggests.add(addSuggest);
                    }
                }

                String candidate = suggests.size() > 0
                        ? suggests.get(0).getTitle()
                        : null;

                return new SuggestResponse(query, candidate, BASE_SEARCH_URL, suggests);
            } catch (IndexOutOfBoundsException e) {
                throw new IOException("Failed to parse JSON", e);
            }
        }
    }
}
