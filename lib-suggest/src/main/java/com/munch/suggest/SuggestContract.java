package com.munch.suggest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.munch.mvp.MvpContract;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;

import java.util.List;

public interface SuggestContract extends MvpContract {
    interface View extends MvpContract.View {
        @UiThread
        void setSuggests(@Nullable String candidate,
                         @Nullable List<Suggest> suggests);

        @UiThread
        void setSuggestInteractor(@NonNull SuggestInteractor.Factory suggestInteractorFactory);

        @UiThread
        void setUserQuery(@Nullable String query);
    }
    interface Presenter extends MvpContract.Presenter<View> {
        @UiThread
        void setInteractorFactory(@Nullable SuggestInteractor.Factory suggestInteractorFactory);

        @UiThread
        void setQuery(@Nullable String query);
    }
}
