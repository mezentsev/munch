package com.munch.browser.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardHelper {
    private static String TAG = "[MNCH:KeyboardHelper]";

    public static void hideKeyboard(@NonNull Context context,
                                    @NonNull View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            Log.d(TAG, "Can't hide keyboard");
        }
    }

    public static void showKeyboard(@NonNull Context context,
                                    @NonNull View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            Log.d(TAG, "Can't show keyboard");
        }
    }
}
