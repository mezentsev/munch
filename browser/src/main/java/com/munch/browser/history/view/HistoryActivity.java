package com.munch.browser.history.view;

import android.os.Bundle;

import com.munch.browser.R;
import com.munch.browser.base.view.BaseActivity;

public class HistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.munch_browser_simple_activity);

        if (savedInstanceState == null) {
            addFragment(R.id.munch_main_container, new HistoryFragment());
        }
    }
}
