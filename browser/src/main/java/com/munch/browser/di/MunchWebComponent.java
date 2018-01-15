package com.munch.browser.di;

import com.munch.mvp.ActivityScoped;
import com.munch.webview.di.WebModule;

import dagger.Component;

@ActivityScoped
@Component(dependencies = AppComponent.class, modules = WebModule.class)
public interface MunchWebComponent {
}
