package com.example.listdetailapplication.di.component;

import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.detail.DetailActivity;
import com.example.listdetailapplication.di.annotations.ListActivityScope;
import com.example.listdetailapplication.di.modules.DetailActivityModule;
import com.example.listdetailapplication.di.modules.ListActivityModule;

import dagger.Component;

@ListActivityScope
@Component(modules = DetailActivityModule.class, dependencies = ApplicationComponent.class)
public interface DetailActivityComponent {
    void injectHomeActivity(DetailActivity listActivity);
}