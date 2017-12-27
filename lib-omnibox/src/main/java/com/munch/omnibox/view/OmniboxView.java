package com.munch.omnibox.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
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
        View view = inflate(getContext(), R.layout.munch_omnibox_view, this);
        View crossView = view.findViewById(R.id.munch_omnibox_cross);
        EditText searchView = view.findViewById(R.id.munch_omnibox_search);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    crossView.setVisibility(VISIBLE);
                } else {
                    crossView.setVisibility(GONE);
                }
            }
        });

        crossView.setOnClickListener((View v) -> {
            searchView.setText("");
        });
    }
}
