package pro.mezentsev.munch.browser.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import pro.mezentsev.munch.browser.R;
import pro.mezentsev.munch.browser.base.view.BaseActivity;
import pro.mezentsev.munch.browser.bookmarks.view.BookmarksFragment;
import pro.mezentsev.munch.browser.history.view.HistoryFragment;

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
                    .add(R.id.munch_history_container, new HistoryFragment())
                    .commit();
        }
    }
}
