package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;

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

public class YaConverterFactory extends Converter.Factory {
    private static final String BASE_SEARCH_URL = "https://yandex.ru/search/?text=";

    @Override
    public Converter<ResponseBody, SuggestResponse> responseBodyConverter(@NonNull Type type,
                                                                          @NonNull Annotation[] annotations,
                                                                          @NonNull Retrofit retrofit) {
        return JsonConverter.INSTANCE;
    }

    final static class JsonConverter implements Converter<ResponseBody, SuggestResponse> {
        static final JsonConverter INSTANCE = new JsonConverter();

        private JsonConverter() {

        }

        @Override
        public SuggestResponse convert(@NonNull ResponseBody responseBody) throws IOException {
            try {
                JsonArray jsonArray = new Gson().fromJson(responseBody.string(), JsonArray.class);
                List<Suggest> suggests = new ArrayList<>();
                int suggestionId = 0;

                String query = jsonArray.get(suggestionId++).getAsString();
                String candidate = jsonArray.get(suggestionId++).getAsString();
                //JsonArray wordSuggests = jsonArray.get(2).getAsJsonArray();

                // check nav and fact suggestions
                for (int i = suggestionId; i <= 5; ++i) {
                    if (jsonArray.size() > i) {
                        JsonElement jsonElement = jsonArray.get(i);
                        if (jsonElement.isJsonObject()) {
                            break;
                        }

                        if (jsonElement.isJsonArray()) {
                            ++suggestionId;

                            JsonArray array = jsonElement.getAsJsonArray();
                            if (array.size() == 5) { // nav
                                String title = array.get(1).getAsString();
                                Uri url = Uri.parse(array.get(3).getAsString());

                                suggests.add(
                                        SuggestFactory.createNavigationSuggest(
                                                title,
                                                url
                                        ));
                            } else if (array.size() == 3) { // fact
                                String title = array.get(1).getAsString();
                                String description = array.get(2).getAsString();
                                Uri url = Uri.parse(BASE_SEARCH_URL + title);

                                suggests.add(
                                        SuggestFactory.createFactSuggest(
                                                title,
                                                url,
                                                description
                                        ));
                            }
                        }
                    }
                }

                // parse text suggestions
                JsonObject jsonObject = jsonArray.get(suggestionId).getAsJsonObject();
                JsonElement suggestionsObject = jsonObject.get("suggestions");
                if (suggestionsObject.isJsonArray()) {
                    JsonArray suggestionsAsJsonArray = suggestionsObject.getAsJsonArray();

                    for (int i = 0; i < suggestionsAsJsonArray.size(); ++i) {
                        JsonArray suggestJsonArray = suggestionsAsJsonArray.get(i).getAsJsonArray();
                        String title = suggestJsonArray.get(0).getAsString();
                        Uri url = Uri.parse(BASE_SEARCH_URL + title);
                        double weight = suggestJsonArray.get(1).getAsDouble();

                        suggests.add(SuggestFactory.createTextSuggest(
                                title,
                                url,
                                weight
                        ));
                    }
                }

                return new SuggestResponse(query, candidate, suggests);
            } catch (IndexOutOfBoundsException e) {
                throw new IOException("Failed to parse JSON", e);
            }
        }
    }
}
