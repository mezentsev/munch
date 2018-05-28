package pro.mezentsev.munch.suggest.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Suggest interface.
 */
public interface Suggest {
    /**
     * Get suggest title.
     *
     * @return title
     */
    @NonNull
    String getTitle();

    /**
     * Get suggest description.
     *
     * @return description
     */
    @Nullable
    String getDescription();

    /**
     * Get suggest type.
     *
     * @return type
     */
    @Nullable
    SuggestType getType();

    /**
     * Get suggest url.
     * It can be search or site url.
     *
     * @return url
     */
    @Nullable
    Uri getUrl();

    /**
     * Suggest priority.
     *
     * @return weight
     */
    double getWeight();

    enum SuggestType{
        NAV,
        FACT,
        TEXT
    }
}
