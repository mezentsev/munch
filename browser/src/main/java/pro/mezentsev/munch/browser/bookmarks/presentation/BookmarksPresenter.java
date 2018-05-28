package pro.mezentsev.munch.browser.bookmarks.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import pro.mezentsev.munch.bookmarks.model.Bookmark;
import pro.mezentsev.munch.browser.bookmarks.BookmarksContract;
import pro.mezentsev.munch.mvp.FlowableRepository;
import pro.mezentsev.munch.mvp.FragmentScoped;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

@FragmentScoped
public final class BookmarksPresenter implements BookmarksContract.Presenter {

    @NonNull
    private final String TAG = "[MNCH:BPresenter]";

    @NonNull
    private final FlowableRepository<Bookmark> mBookmarksRepository;
    @NonNull
    private final Executor mMainThreadExecutor;
    @NonNull
    private final CompositeDisposable mCompositeDisposable;

    @Nullable
    private BookmarksContract.View mView;

    @Inject
    public BookmarksPresenter(@NonNull FlowableRepository<Bookmark> bookmarksRepository,
                              @NonNull Executor mainThreadExecutor) {
        mBookmarksRepository = bookmarksRepository;
        mMainThreadExecutor = mainThreadExecutor;
        mCompositeDisposable = new CompositeDisposable();

        mBookmarksRepository.get();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @Override
    public void attachView(@NonNull BookmarksContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadAll() {
        Log.d(TAG, "Try to load bookmarks");

        mCompositeDisposable.clear();
        mCompositeDisposable.add(
                mBookmarksRepository.get()
                        .observeOn(Schedulers.from(mMainThreadExecutor))
                        .subscribeWith(new DisposableSubscriber<List<Bookmark>>() {
                            @Override
                            public void onNext(List<Bookmark> bookmarkList) {
                                if (mView != null) {
                                    mView.show(bookmarkList);
                                    Log.d(TAG, "onNext " + bookmarkList);
                                } else {
                                    Log.d(TAG, "No view attached. Ignored.");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Load failed", e);
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Load complete");
                            }
                        })
        );
    }
}
