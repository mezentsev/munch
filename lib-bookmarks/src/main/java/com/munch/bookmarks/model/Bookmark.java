package com.munch.bookmarks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "bookmarks",
        indices = {@Index(value = {"position"}, unique = true)})
public final class Bookmark implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "position")
    private long mPosition;

    @ColumnInfo(name = "timestamp")
    private long mTimestamp;

    @ColumnInfo(name = "url")
    private String mUrl;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "favicon")
    private String mFavicon;

    @ColumnInfo(name = "background")
    private String mBackground;

    @ColumnInfo(name = "important")
    private boolean mIsImportant;

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public long getPosition() {
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
    public String getFavicon() {
        return mFavicon;
    }

    @Nullable
    public String getBackground() {
        return mBackground;
    }

    public boolean getIsImportant() {
        return mIsImportant;
    }

    @Ignore
    public Bookmark(@NonNull String url,
                    @Nullable String title) {
        mUrl = url;
        mTitle = title;
        mIsImportant = true;
    }

    public Bookmark(@NonNull String url,
                    long timestamp,
                    long position,
                    @Nullable String title,
                    @Nullable String description,
                    @Nullable String favicon,
                    @Nullable String background,
                    boolean isImportant) {
        mUrl = url;
        mTimestamp = timestamp;
        mPosition = position;
        mTitle = title;
        mDescription = description;
        mFavicon = favicon;
        mBackground = background;
        mIsImportant = isImportant;
    }

    @Override
    @NonNull
    public String toString() {
        return "Bookmark url " + mUrl;
    }

    public void setPosition(long position) {
        mPosition = position;
    }

    public void setTimestamp(long position) {
        mPosition = position;
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
        dest.writeLong(mPosition);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mFavicon);
        dest.writeString(mBackground);
    }

    public static final Parcelable.Creator<Bookmark> CREATOR = new Parcelable.Creator<Bookmark>() {
        public Bookmark createFromParcel(Parcel in) {
            return new Bookmark(in);
        }

        public Bookmark[] newArray(int size) {
            return new Bookmark[size];
        }
    };

    private Bookmark(Parcel parcel) {
        mUrl = parcel.readString();
        mTimestamp = parcel.readLong();
        mPosition = parcel.readLong();
        mTitle = parcel.readString();
        mDescription = parcel.readString();
        mFavicon = parcel.readString();
        mBackground = parcel.readString();
    }
}
