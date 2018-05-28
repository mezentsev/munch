package pro.mezentsev.munch.browser.history;

import android.support.annotation.NonNull;

import pro.mezentsev.munch.history.model.History;
import pro.mezentsev.munch.mvp.MvpContract;

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
