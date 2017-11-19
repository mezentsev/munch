package com.munch.suggest.data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.munch.suggest.R;
import com.munch.suggest.model.Suggest;

import java.util.List;

public final class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.SuggestViewHolder> {
    private final static String TAG = SuggestAdapter.class.getSimpleName();

    @NonNull
    private final Context mContext;
    @Nullable
    private List<Suggest> mSuggests;
    private Suggest.SuggestType[] mSuggestTypes = Suggest.SuggestType.values();

    public SuggestAdapter(@NonNull Context context) {
        mContext = context;
    }

    @UiThread
    public void setSuggests(@Nullable List<Suggest> suggests) {
        mSuggests = suggests;
        notifyDataSetChanged();
    }

    @Override
    public SuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        View view;
        switch (mSuggestTypes[viewType]) {
            case TEXT:
                view = LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.munch_suggest_text_suggest, parent, false);
                return new TextSuggestViewHolder(view);
            case NAV:
                view = LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.munch_suggest_navigation_suggest, parent, false);

                return new NavigationSuggestViewHolder(view);
            case FACT:
                view = LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.munch_suggest_fact_suggest, parent, false);
                return new FactSuggestViewHolder(view);
            default:
                throw new IllegalStateException("Unsupported type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestViewHolder holder,
                                 int position) {
        Suggest suggest = mSuggests.get(position);
        switch (suggest.getType()) {
            case TEXT:
                ((TextSuggestViewHolder) holder).bind(suggest.getTitle());
                break;
            case NAV:
                ((NavigationSuggestViewHolder) holder).bind(suggest.getUrl());
                break;
            case FACT:
                ((FactSuggestViewHolder) holder).bind(
                        suggest.getTitle(),
                        suggest.getDescription());
                break;
            default:
                throw new IllegalStateException("Unsupported type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mSuggests.get(position).getType().ordinal();
    }

    @Override
    public int getItemCount() {
        if (mSuggests != null) {
            return mSuggests.size();
        }

        return 0;
    }

    abstract static class SuggestViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        protected final TextView mTitle;

        SuggestViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.munch_suggest_title);
        }
    }

    static class TextSuggestViewHolder extends SuggestViewHolder {

        public TextSuggestViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(@NonNull String title) {
            mTitle.setText(title);
        }
    }

    static class NavigationSuggestViewHolder extends SuggestViewHolder {

        public NavigationSuggestViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(@NonNull Uri url) {
            mTitle.setText(url.toString());
        }
    }

    static class FactSuggestViewHolder extends SuggestViewHolder {

        @NonNull
        private final TextView mDescription;

        public FactSuggestViewHolder(@NonNull View itemView) {
            super(itemView);
            mDescription = itemView.findViewById(R.id.munch_suggest_description);
        }

        void bind(@NonNull String title,
                  @Nullable String description) {
            mTitle.setText(title);
            mDescription.setText(description);
        }
    }
}
