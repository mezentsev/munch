package pro.mezentsev.munch.browser.base.view;

import android.graphics.Rect;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private boolean mKeyboardListenersAttached = false;
    private boolean mWasShown = false;

    @NonNull
    private ViewGroup mRootLayout;

    @NonNull
    private ViewTreeObserver.OnGlobalLayoutListener mKeyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @NonNull
        private Rect mRect = new Rect();

        @Override
        public void onGlobalLayout() {
            // navigation bar height
            int navigationBarHeight = 0;
            int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // status bar height
            int statusBarHeight = 0;
            resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // display window size for the app layout
            getWindow().getDecorView().getWindowVisibleDisplayFrame(mRect);

            // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
            int keyboardHeight = mRootLayout.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + mRect.height());

            if (keyboardHeight <= 0) {
                if (mWasShown) {
                    onHideKeyboard();
                }
            } else {
                mWasShown = true;
                onShowKeyboard(keyboardHeight);
            }
        }
    };

    protected void onShowKeyboard(int keyboardHeight) {
    }

    protected void onHideKeyboard() {
    }

    protected void attachKeyboardListeners(@IdRes int rootLayout) {
        if (mKeyboardListenersAttached) {
            return;
        }

        mRootLayout = findViewById(rootLayout);
        mRootLayout.getViewTreeObserver().addOnGlobalLayoutListener(mKeyboardLayoutListener);

        mKeyboardListenersAttached = true;
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();

        if (mKeyboardListenersAttached) {
            mRootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(mKeyboardLayoutListener);
        }
    }
}
