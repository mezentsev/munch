package pro.mezentsev.munch.browser.suggest.view;

import android.content.Context;
import android.os.Bundle;

import pro.mezentsev.munch.browser.R;
import pro.mezentsev.munch.browser.base.view.BaseActivity;

import javax.inject.Inject;

public class SuggestActivity extends BaseActivity {

    @Inject
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_simple_activity);

        attachKeyboardListeners(R.id.root_layout);

        if (savedInstanceState == null) {
            addFragment(R.id.munch_main_container, new SuggestFragment());
        }
    }

    @Override
    protected void onHideKeyboard() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
