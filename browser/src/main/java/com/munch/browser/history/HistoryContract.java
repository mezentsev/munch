package com.munch.browser.history;

import android.support.annotation.NonNull;

import com.munch.history.model.History;
import com.munch.mvp.MvpContract;

import java.util.List;

public interface HistoryContract extends MvpContract {
    interface View extends MvpContract.View {
        void showHistory(@NonNull List<History> history);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void loadHistory();
        void removeHistory(@NonNull History history);
    }

    interface HistoryListener {
        void onHistoryClicked(@NonNull History history);
        void onHistoryRemoved(@NonNull History history);
    }
}
