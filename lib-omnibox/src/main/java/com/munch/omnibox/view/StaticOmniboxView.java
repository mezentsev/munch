package com.munch.omnibox.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.munch.mvp.MvpView;
import com.munch.omnibox.R;

public final class StaticOmniboxView
        extends LinearLayout implements MvpView {
    public StaticOmniboxView(@NonNull Context context) {
        this(context, null, 0);
    }

    public StaticOmniboxView(@NonNull Context context,
                             @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaticOmniboxView(@NonNull Context context,
                             @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.munch_static_omnibox_view, this);
    }
}
