package pro.mezentsev.munch.suggest.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pro.mezentsev.munch.suggest.data.SuggestResponse;
import pro.mezentsev.munch.suggest.data.GoSuggestApi;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public final class GoSuggestInteractor implements SuggestInteractor {
    private static final String BASE_URL = "https://google.com/";

    @NonNull
    private final GoSuggestApi mGoSuggestApi;

    GoSuggestInteractor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new GoConverterFactory())
                .build();

        mGoSuggestApi = retrofit.create(GoSuggestApi.class);
    }

    @Override
    public Single<SuggestResponse> getSuggests(@NonNull RequestSpecification specification) {
        return mGoSuggestApi.get(specification.build());
    }

    @NonNull
    @Override
    public RequestSpecification.Factory getSpecificationFactory() {
        return new GoRequestSpecification.Factory();
    }

    public static class Factory implements SuggestInteractor.Factory {
        @Nullable
        private static GoSuggestInteractor mSuggestInteractor;

        public Factory() {
        }

        @NonNull
        @Override
        public GoSuggestInteractor get() {
            if (mSuggestInteractor == null) {
                mSuggestInteractor = new GoSuggestInteractor();
            }

            return mSuggestInteractor;
        }
    }
}
