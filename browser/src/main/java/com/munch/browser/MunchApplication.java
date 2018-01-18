package com.munch.browser;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import com.munch.browser.di.DaggerAppComponent;

public class MunchApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
