package pro.mezentsev.munch.browser.history.view;

import android.os.Bundle;

import pro.mezentsev.munch.browser.R;
import pro.mezentsev.munch.browser.base.view.BaseActivity;

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
