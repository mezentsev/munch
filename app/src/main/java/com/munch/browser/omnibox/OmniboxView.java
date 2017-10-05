package com.munch.browser.omnibox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.munch.browser.R;

public final class OmniboxView extends FrameLayout {
    public OmniboxView(@NonNull Context context) {
        this(context, null, 0);
    }

    public OmniboxView(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OmniboxView(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(getContext(), R.layout.munch_omnibox, null);
        addView(view);
    }
}
