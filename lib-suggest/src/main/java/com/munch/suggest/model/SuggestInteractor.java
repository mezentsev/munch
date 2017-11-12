package com.munch.suggest.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;

public interface SuggestInteractor {
    @NonNull
    Call<List<Suggest>> getSuggests(@Nullable String query);
}
