package com.munch.browser.main;

import com.munch.browser.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainActivityModule {
    // TODO: 15.01.18 activity -> view
    @ActivityScoped
    @Binds
    abstract MainActivity provideMainActivity(MainActivity mainActivity);

    @ActivityScoped
    @Binds
    abstract MainFragment provideMainFragment(MainFragment mainFragment);
}
