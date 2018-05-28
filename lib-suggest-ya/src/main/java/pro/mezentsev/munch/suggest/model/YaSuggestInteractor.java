package pro.mezentsev.munch.suggest.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pro.mezentsev.munch.suggest.data.SuggestResponse;
import pro.mezentsev.munch.suggest.data.YaSuggestApi;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pro.mezentsev.munch.suggest.data.SuggestResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class YaSuggestInteractor implements SuggestInteractor {
    private static final String BASE_URL = "http://yandex.ru/";

    @NonNull
    private final YaSuggestApi mYaSuggestApi;

    YaSuggestInteractor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new YaConverterFactory())
                .build();

        mYaSuggestApi = retrofit.create(YaSuggestApi.class);
    }

    @Override
    public Single<SuggestResponse> getSuggests(@NonNull RequestSpecification specification) {
        return mYaSuggestApi.get(specification.build());
    }

    @NonNull
    @Override
    public RequestSpecification.Factory getSpecificationFactory() {
        return new YaRequestSpecification.Factory();
    }

    public static class Factory implements SuggestInteractor.Factory {
        @Nullable
        private static YaSuggestInteractor mSuggestInteractor;

        public Factory() {
        }

        @NonNull
        @Override
        public YaSuggestInteractor get() {
            if (mSuggestInteractor == null) {
                mSuggestInteractor = new YaSuggestInteractor();
            }

            return mSuggestInteractor;
        }
    }
}
