package com.example.listdetailapplication.di.component;


import com.example.listdetailapplication.ApiService;
import com.example.listdetailapplication.CommonRepository;
import com.example.listdetailapplication.PrefUtils;
import com.example.listdetailapplication.ViewModelProviderFactory;
import com.example.listdetailapplication.di.ViewModelFactoryModule;
import com.example.listdetailapplication.di.annotations.ApplicationScope;
import com.example.listdetailapplication.di.modules.DataModule;
import com.example.listdetailapplication.di.modules.NetworkModule;
import com.example.listdetailapplication.di.modules.RepositoryModule;
import com.squareup.picasso.Picasso;

import dagger.Component;


@ApplicationScope
@Component(modules = {NetworkModule.class, DataModule.class, RepositoryModule.class, ViewModelFactoryModule.class,
})
public interface ApplicationComponent {
    Picasso picasso();
    CommonRepository commonRepository();
    ViewModelProviderFactory viewModelProvideFactory();
}
