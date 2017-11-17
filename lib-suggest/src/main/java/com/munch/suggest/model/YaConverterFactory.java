package com.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.munch.suggest.data.SuggestResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.munch.suggest.model.Suggest.SuggestType.FACT;
import static com.munch.suggest.model.Suggest.SuggestType.NAV;
import static com.munch.suggest.model.Suggest.SuggestType.TEXT;

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

                String query = jsonArray.get(0).getAsString();
                String candidate = jsonArray.get(1).getAsString();
                //JsonArray wordSuggests = jsonArray.get(2).getAsJsonArray();

                int suggestionsId = 2;
                for (int i = suggestionsId; i <= 5; ++i) {
                    if (jsonArray.size() > i) {
                        JsonElement jsonElement = jsonArray.get(i);
                        if (jsonElement.isJsonObject()) {
                            break;
                        }

                        if (jsonElement.isJsonArray()) {
                            ++suggestionsId;

                            JsonArray array = jsonElement.getAsJsonArray();
                            if (array.size() == 5) { // nav
                                String title = array.get(1).getAsString();
                                Uri url = Uri.parse(array.get(3).getAsString());
                                suggests.add(new FullSuggest(
                                        title,
                                        url,
                                        0.,
                                        null,
                                        NAV
                                ));
                            } else if (array.size() == 3) { // fact
                                String title = array.get(1).getAsString();
                                String description = array.get(2).getAsString();
                                Uri url = Uri.parse(BASE_SEARCH_URL + title);
                                suggests.add(new FullSuggest(
                                        title,
                                        url,
                                        0.,
                                        description,
                                        FACT
                                ));
                            }
                        }
                    }
                }

                JsonObject jsonObject = jsonArray.get(suggestionsId).getAsJsonObject();

                JsonElement suggestionsObject = jsonObject.get("suggestions");
                if (suggestionsObject.isJsonArray()) {
                    JsonArray suggestionsAsJsonArray = suggestionsObject.getAsJsonArray();

                    for (int i = 0; i < suggestionsAsJsonArray.size(); ++i) {
                        JsonArray suggestJsonArray = suggestionsAsJsonArray.get(i).getAsJsonArray();
                        String title = suggestJsonArray.get(0).getAsString();
                        Uri url = Uri.parse(BASE_SEARCH_URL + title);
                        double weight = suggestJsonArray.get(1).getAsDouble();

                        suggests.add(new FullSuggest(
                                title,
                                url,
                                weight,
                                null,
                                TEXT
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
