package pro.mezentsev.munch.suggest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import pro.mezentsev.munch.mvp.MvpContract;
import pro.mezentsev.munch.suggest.data.SuggestClicklistener;
import pro.mezentsev.munch.suggest.model.Suggest;
import pro.mezentsev.munch.suggest.model.SuggestInteractor;

import java.util.List;

public interface SuggestContract extends MvpContract {
    interface View extends MvpContract.View {
        @UiThread
        void setSuggests(@Nullable String candidate,
                         @Nullable List<Suggest> suggests);

        @UiThread
        void setReversed(boolean isReversed);

        @UiThread
        void setSuggestInteractor(@NonNull SuggestInteractor.Factory suggestInteractorFactory);

        @UiThread
        void setUserQuery(@Nullable String query);

        @UiThread
        void setSuggestClickListener(@Nullable SuggestClicklistener suggestClickListener);
    }
    interface Presenter extends MvpContract.Presenter<View> {
        @UiThread
        void setInteractorFactory(@Nullable SuggestInteractor.Factory suggestInteractorFactory);

        @UiThread
        void setQuery(@Nullable String query);
    }
}
