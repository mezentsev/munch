package com.munch.helpers;

import android.support.annotation.NonNull;

/**
 * Lazy initialization interface.
 *
 * @param <T>
 */
public interface Lazy<T> {
    /**
     * Get instance.
     *
     * @return instance.
     */
    @NonNull
    T get();
}
