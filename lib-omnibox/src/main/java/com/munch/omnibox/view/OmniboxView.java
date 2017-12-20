package com.munch.omnibox.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.munch.mvp.MvpView;
import com.munch.omnibox.R;

public final class OmniboxView
        extends LinearLayout implements MvpView {
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
        inflate(getContext(), R.layout.munch_omnibox_view, this);
    }
}
