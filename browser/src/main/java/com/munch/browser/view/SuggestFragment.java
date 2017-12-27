package com.munch.browser.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.munch.browser.R;
import com.munch.browser.helpers.KeyboardHelper;
import com.munch.suggest.SuggestContract;
import com.munch.suggest.data.SuggestClicklistener;
import com.munch.suggest.model.GoSuggestInteractor;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestFactory;

public class SuggestFragment extends Fragment {

    private static String TAG = "[MNCH:SuggestFragment]";

    // TODO: 21.12.17 read from preferences
    private static String SEARCH_ENGINE_URI = "https://google.com/search?q=";

    @NonNull
    private EditText mOmniboxView;
    @NonNull
    private SuggestContract.View mSuggestView;

    @NonNull
    public static SuggestFragment newInstance() {
        SuggestFragment f = new SuggestFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        KeyboardHelper.showKeyboard(getContext(), mOmniboxView);

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

    private void openUrl(@NonNull Suggest suggest) {
        Log.d(TAG, "Selected: " + suggest.getTitle());

        Uri url = suggest.getUrl();
        if (url == null) {
            url = Uri.parse(SEARCH_ENGINE_URI + suggest.getTitle());
        }

        WebViewFragment webViewFragment = WebViewFragment.newInstance(url);

        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.munch_main_container, webViewFragment)
                .commit();
    }
}
