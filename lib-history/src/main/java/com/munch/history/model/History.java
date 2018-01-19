package com.munch.history.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.Timestamp;
import java.util.UUID;

@Entity(tableName = "history")
public final class History {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String mId;

    @NonNull
    @ColumnInfo(name = "url")
    private final String mUrl;

    @NonNull
    @ColumnInfo(name = "timestamp")
    private final String mTimestamp;

    @Nullable
    @ColumnInfo(name = "title")
    private final String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private final String mDescription;

    @Nullable
    @ColumnInfo(name = "html")
    private final String mHtml;

    @Nullable
    @ColumnInfo(name = "favicon")
    private final String mFavicon;

    @Nullable
    @ColumnInfo(name = "background")
    private final String mBackground;

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    @NonNull
    public String getTimestamp() {
        return mTimestamp;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getHtml() {
        return mHtml;
    }

    @Nullable
    public String getFavicon() {
        return mFavicon;
    }

    @Nullable
    public String getBackground() {
        return mBackground;
    }

    @Ignore
    public History(@NonNull String url) {
        this(
                url,
                null,
                null,
                null
        );
    }

    @Ignore
    public History(@NonNull String url,
                   @Nullable String title,
                   @Nullable String description,
                   @Nullable String html) {
        this(
                UUID.randomUUID().toString(),
                url,
                ((Long) (System.currentTimeMillis() / 1000)).toString(),
                title,
                description,
                html,
                null,
                null
        );
    }

    public History(@NonNull String id,
                   @NonNull String url,
                   @NonNull String timestamp,
                   @Nullable String title,
                   @Nullable String description,
                   @Nullable String html,
                   @Nullable String favicon,
                   @Nullable String background) {
        mId = id;
        mUrl = url;
        mTimestamp = timestamp;
        mTitle = title;
        mDescription = description;
        mHtml = html;
        mFavicon = favicon;
        mBackground = background;
    }

    @Override
    @NonNull
    public String toString() {
        return "History url " + mUrl;
    }
}
