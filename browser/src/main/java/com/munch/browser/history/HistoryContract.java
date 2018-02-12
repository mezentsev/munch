package com.munch.browser.history;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.history.model.History;
import com.munch.mvp.MvpContract;

import java.util.List;

public interface HistoryContract extends MvpContract {
    interface View extends MvpContract.View {
        void showHistory();

        void informHistoryLoaded(@Nullable List<History> history);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void loadHistory();
    }

    interface HistoryListener {
        void onHistoryClicked(@NonNull History history);
    }
}
