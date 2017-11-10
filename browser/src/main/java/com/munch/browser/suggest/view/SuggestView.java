package com.munch.browser.suggest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.munch.browser.suggest.SuggestContract;

public class SuggestView extends LinearLayout implements SuggestContract.View {
    public SuggestView(@NonNull Context context) {
        this(context, null);
    }

    public SuggestView(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuggestView(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
