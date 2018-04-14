package com.munch.history.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Entity(tableName = "history")
public final class History implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "timestamp")
    private long mTimestamp;

    @NonNull
    @ColumnInfo(name = "url")
    private String mUrl;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    @Nullable
    @ColumnInfo(name = "html")
    private String mHtml;

    @Nullable
    @ColumnInfo(name = "favicon")
    private String mFavicon;

    @Nullable
    @ColumnInfo(name = "background")
    private String mBackground;

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    @NonNull
    public long getTimestamp() {
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
    public History(long timestamp,
                   @NonNull String url,
                   @Nullable String title) {
        this(
                url,
                timestamp,
                title,
                null,
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
                url,
                System.currentTimeMillis(),
                title,
                description,
                html,
                null,
                null
        );
    }

    public History(@NonNull String url,
                   long timestamp,
                   @Nullable String title,
                   @Nullable String description,
                   @Nullable String html,
                   @Nullable String favicon,
                   @Nullable String background) {
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

    @NonNull
    public String getDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", Locale.US);
            Date netDate = (new Date(mTimestamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    @NonNull
    public String getTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
            Date netDate = (new Date(mTimestamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    public void setFavicon(@NonNull String favicon) {
        mFavicon = favicon;
    }

    public void setUrl(@NonNull String url) {
        mUrl = url;
    }

    public void setTitle(@Nullable String title) {
        mTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeLong(mTimestamp);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mHtml);
        dest.writeString(mFavicon);
        dest.writeString(mBackground);
    }

    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
        // распаковываем объект из Parcel
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        public History[] newArray(int size) {
            return new History[size];
        }
    };

    private History(Parcel parcel) {
        mUrl = parcel.readString();
        mTimestamp = parcel.readLong();
        mTitle = parcel.readString();
        mDescription = parcel.readString();
        mHtml = parcel.readString();
        mFavicon = parcel.readString();
        mBackground = parcel.readString();
    }
}
