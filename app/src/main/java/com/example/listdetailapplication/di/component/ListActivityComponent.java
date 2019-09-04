package com.example.listdetailapplication.di.component;

import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.di.annotations.ListActivityScope;
import com.example.listdetailapplication.di.modules.ListActivityModule;

import dagger.Component;


@ListActivityScope
@Component(modules = ListActivityModule.class, dependencies = ApplicationComponent.class)
public interface ListActivityComponent {
    void injectHomeActivity(ListActivity listActivity);
}
