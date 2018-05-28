package pro.mezentsev.munch.mvp;

import android.support.annotation.NonNull;

import java.util.List;

public interface Repository<T> {
    List<T> get();

    void save(@NonNull T item);

    void remove(@NonNull T item);
}
