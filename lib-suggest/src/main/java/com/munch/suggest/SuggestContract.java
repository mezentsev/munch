package com.munch.suggest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.munch.mvp.MvpContract;
import com.munch.suggest.model.Suggest;

import java.util.List;

public interface SuggestContract extends MvpContract {
    interface View extends MvpContract.View {
        @UiThread
        void setSuggests(@Nullable List<Suggest> suggests);
    }
    interface Presenter extends MvpContract.Presenter<View> {
        @UiThread
        void setQuery(@NonNull String query);
    }
}
