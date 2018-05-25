package com.munch.browser.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.munch.browser.R;
import com.munch.browser.base.view.BaseActivity;
import com.munch.browser.bookmarks.view.BookmarksFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);

        if (savedInstanceState == null) {
            FragmentManager mFragmentManager = getSupportFragmentManager();

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_bookmarks_container, new BookmarksFragment())
                    .add(R.id.munch_main_container, new MainFragment())
                    .commit();
        }
    }
}
