package pro.mezentsev.munch.browser.di;

import android.app.Application;

import pro.mezentsev.munch.browser.MunchApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is a Dagger component.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created.
 * //{@link AndroidSupportInjectionModule}
 * // is the module from Dagger.Android that helps with the generation
 * // and location of subcomponents.
 */
@Singleton
@Component(modules = {
        HistoryRepositoryModule.class,
        BookmarksRepositoryModule.class,
        MunchWebActivityModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MunchApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
