package com.munch.browser.di;

import com.munch.webview.di.MunchWebModule;

import dagger.Component;

@ActivityScoped
@Component(dependencies = AppComponent.class, modules = MunchWebModule.class)
public interface MunchWebComponent {
}
