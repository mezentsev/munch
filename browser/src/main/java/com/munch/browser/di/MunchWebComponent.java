package com.munch.browser.di;

import com.munch.browser.web.WebActivityModule;
import com.munch.mvp.ActivityScoped;

import dagger.Component;

@ActivityScoped
@Component(dependencies = AppComponent.class,
        modules = {
                WebActivityModule.class
        })
public interface MunchWebComponent {
}
