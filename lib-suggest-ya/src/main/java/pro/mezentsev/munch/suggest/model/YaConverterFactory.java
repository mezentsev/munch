package pro.mezentsev.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pro.mezentsev.munch.suggest.data.SuggestResponse;

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

        private JsonConverter() {

        }

        @Override
        public SuggestResponse convert(@NonNull ResponseBody responseBody) throws IOException {
            try {
                JsonArray jsonArray = new Gson().fromJson(responseBody.string(), JsonArray.class);
                List<Suggest> suggests = new ArrayList<>();
                int suggestionId = PARSE_START_ID;

                String query = jsonArray.get(suggestionId++).getAsString();
                String candidate = jsonArray.get(suggestionId++).getAsString();

                // check nav and fact suggestions
                for (int i = suggestionId; i <= PARSE_NAV_FACT_STOP_ID; ++i) {
                    if (jsonArray.size() > i) {
                        JsonElement jsonElement = jsonArray.get(i);
                        if (jsonElement.isJsonObject()) {
                            break;
                        }

                        if (jsonElement.isJsonArray()) {
                            ++suggestionId;

                            JsonArray array = jsonElement.getAsJsonArray();
                            if (array.size() == PARSE_NAV_SIZE) { // nav
                                String title = array.get(PARSE_NAV_TITLE_ID).getAsString();
                                Uri url = Uri.parse(array.get(PARSE_NAV_URL_ID).getAsString());

                                suggests.add(
                                        SuggestFactory.createNavigationSuggest(
                                                title,
                                                url
                                        ));
                            } else if (array.size() == PARSE_FACT_SIZE) { // fact
                                String title = array.get(PARSE_FACT_TITLE_ID).getAsString();
                                String description = array.get(PARSE_FACT_DESCRIPTION_ID).getAsString();

                                suggests.add(
                                        SuggestFactory.createFactSuggest(
                                                title,
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
                        String title = suggestJsonArray.get(PARSE_TEXT_TITLE_ID).getAsString();
                        double weight = suggestJsonArray.get(PARSE_TEXT_WEIGHT_ID).getAsDouble();

                        suggests.add(SuggestFactory.createTextSuggest(
                                title,
                                weight
                        ));
                    }
                }

                return new SuggestResponse(query, candidate, BASE_SEARCH_URL, suggests);
            } catch (IndexOutOfBoundsException e) {
                throw new IOException("Failed to parse JSON", e);
            }
        }
    }
}
