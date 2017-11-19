package com.munch.browser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.munch.suggest.model.YaSuggestInteractor;
import com.munch.suggest.view.SuggestView;

public class MunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munch);

        SuggestView suggestView = findViewById(R.id.munch_suggest_view);
        suggestView.setSuggestInteractor(new YaSuggestInteractor.Factory());
        suggestView.setSuggestClickListener(suggest -> {
            Toast.makeText(this, "Selected: " + suggest.getTitle(), Toast.LENGTH_SHORT).show();
        });

        EditText omniboxEditText = findViewById(R.id.munch_omnibox_search);
        omniboxEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                suggestView.setUserQuery(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
