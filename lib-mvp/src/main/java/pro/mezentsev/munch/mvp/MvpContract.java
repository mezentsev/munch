package pro.mezentsev.munch.mvp;

public interface MvpContract {
    interface View extends MvpView {

    }

    interface Presenter<V extends View> extends MvpPresenter<V> {

    }

    abstract class SimplePresenter extends MvpPresenter.Simple<View> {

    }
}
