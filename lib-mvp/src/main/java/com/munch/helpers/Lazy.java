package com.munch.helpers;

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
    T get();
}
