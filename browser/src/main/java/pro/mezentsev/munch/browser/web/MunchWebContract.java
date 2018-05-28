package pro.mezentsev.munch.browser.web;

import android.support.annotation.NonNull;

import pro.mezentsev.munch.mvp.MvpContract;

public interface MunchWebContract extends MvpContract {
    interface View extends MvpContract.View {
        /**
         * Show downloading progress.
         * @param progress from 0 to 100
         */
        void showProgress(int progress);

        /**
         * Display back button.
         */
        void showBackButton(boolean isShow);

        /**
         * Display forward button.
         */
        void showForwardButton(boolean isShow);

        /**
         * Prevent refreshing by swipe animation.
         */
        void stopRefreshBySwipe();

        /**
         * Turn on refreshin by swipe.
         */
        void enableRefreshBySwipe(boolean enable);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        /**
         * Load website by url.
         */
        void useUrl(@NonNull String url);

        /**
         * Reload current page.
         */
        void reload();

        /**
         * Go to previous page.
         */
        void goBack();

        /**
         * Go back to next page.
         */
        void goForward();
    }
}
