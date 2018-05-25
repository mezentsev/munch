package com.munch.browser.history;

import android.support.annotation.NonNull;

import com.munch.history.model.History;
import com.munch.mvp.MvpContract;

import java.util.List;

public interface HistoryContract extends MvpContract {
    interface View extends MvpContract.View {
        void show(@NonNull List<History> history);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void loadAll();
        void remove(@NonNull History history);
    }

    interface HistoryListener {
        void onClicked(@NonNull History history);
        void onRemoved(@NonNull History history);
    }
}
