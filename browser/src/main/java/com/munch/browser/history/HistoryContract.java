package com.munch.browser.history;

import com.munch.mvp.MvpContract;

public interface HistoryContract extends MvpContract {
    interface View extends MvpContract.View {
        void showHistory();

        void informHistoryLoad(int count);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void loadHistory();
    }
}
