package pro.mezentsev.munch.browser.suggest.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import pro.mezentsev.munch.browser.R;
import pro.mezentsev.munch.browser.base.view.BaseFragment;
import pro.mezentsev.munch.browser.helpers.KeyboardHelper;
import pro.mezentsev.munch.browser.web.view.MunchWebActivity;
import pro.mezentsev.munch.suggest.SuggestContract;
import pro.mezentsev.munch.suggest.data.SuggestClicklistener;
import pro.mezentsev.munch.suggest.model.Suggest;
import pro.mezentsev.munch.suggest.model.SuggestFactory;
import pro.mezentsev.munch.suggest.model.SuggestInteractor;

import javax.inject.Inject;

public class SuggestFragment extends BaseFragment {

    private static String TAG = "[MNCH:SuggestFragment]";

    // TODO: 21.12.17 read from preferences
    private static String SEARCH_ENGINE_URI = "https://google.com/search?q=";

    @NonNull
    private EditText mOmniboxView;
    @NonNull
    private SuggestContract.View mSuggestView;

    @Inject
    Context mContext;

    @Inject
    SuggestInteractor.Factory mSuggestInteractorFactory;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_suggest_fragment, container, false);
        mOmniboxView = view.findViewById(R.id.munch_omnibox_search);
        mSuggestView = view.findViewById(R.id.munch_suggest_view);
        setRetainInstance(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // TODO: 15.01.18 move to presenter
        KeyboardHelper.showKeyboard(mContext, mOmniboxView);

        mSuggestView.setReversed(true);

        mSuggestView.setSuggestInteractor(mSuggestInteractorFactory);
        mSuggestView.setSuggestClickListener(new SuggestClicklistener() {
            @Override
            public void onSuggestClicked(@NonNull Suggest suggest) {
                openUrl(suggest);
            }
        });

        mOmniboxView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                mSuggestView.setUserQuery(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mOmniboxView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 0 || event.getAction() == KeyEvent.ACTION_DOWN) {
                    openUrl(SuggestFactory.createSuggest(mOmniboxView.getText().toString()));
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 15.01.18 move to presenter
        mSuggestView.setUserQuery(mOmniboxView.getText().toString());
    }

    // TODO: 15.01.18 move to presenter
    private void openUrl(@NonNull Suggest suggest) {
        Log.d(TAG, "Selected: " + suggest.getTitle());

        Uri url = suggest.getUrl();
        if (url == null) {
            url = Uri.parse(SEARCH_ENGINE_URI + suggest.getTitle());
        }

        Intent intent = new Intent(mContext, MunchWebActivity.class);
        intent.putExtra(MunchWebActivity.EXTRA_URI, url.toString());
        startActivity(intent);
    }
}
