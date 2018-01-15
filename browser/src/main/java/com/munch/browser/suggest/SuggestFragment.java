package com.munch.browser.suggest;

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

import com.munch.browser.R;
import com.munch.browser.helpers.KeyboardHelper;
import com.munch.browser.web.WebActivity;
import com.munch.suggest.SuggestContract;
import com.munch.suggest.model.GoSuggestInteractor;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SuggestFragment extends DaggerFragment {

    private static String TAG = "[MNCH:WebFragment]";

    // TODO: 15.01.18 move to dagger
    // TODO: 21.12.17 read from preferences
    private static String SEARCH_ENGINE_URI = "https://google.com/search?q=";

    @NonNull
    private EditText mOmniboxView;
    @NonNull
    private SuggestContract.View mSuggestView;

    @Inject
    Context mContext;

    @Inject
    public SuggestFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_suggest_fragment, container, false);
        mOmniboxView = view.findViewById(R.id.munch_omnibox_search);
        mSuggestView = view.findViewById(R.id.munch_suggest_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        KeyboardHelper.showKeyboard(mContext, mOmniboxView);

        mSuggestView.setReversed(true);
        mSuggestView.setSuggestInteractor(new GoSuggestInteractor.Factory());
        mSuggestView.setSuggestClickListener(this::openUrl);

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

        mOmniboxView.setOnEditorActionListener((v, actionId, event) -> {
            Log.d(TAG, "" + actionId);
            if (actionId != 0 || event.getAction() == KeyEvent.ACTION_DOWN) {
                openUrl(SuggestFactory.createTextSuggest(mOmniboxView.getText().toString()));
            }

            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSuggestView.setUserQuery(mOmniboxView.getText().toString());
    }

    // TODO: 15.01.18 move to presenter
    private void openUrl(@NonNull Suggest suggest) {
        Log.d(TAG, "Selected: " + suggest.getTitle());

        Uri url = suggest.getUrl();
        if (url == null) {
            url = Uri.parse(SEARCH_ENGINE_URI + suggest.getTitle());
        }

        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URI, url.toString());
        startActivity(intent);

        // TODO: 15.01.18 start Activity
        /*ebFragment munchWebFragment = WebFragment.newInstance(url);

        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.munch_main_container, munchWebFragment)
                .commit();
                */
    }
}
