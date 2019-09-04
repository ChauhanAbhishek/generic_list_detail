package com.example.listdetailapplication.di.modules;

import android.content.Context;


import com.example.listdetailapplication.PrefUtils;
import com.example.listdetailapplication.di.annotations.ApplicationScope;

import dagger.Module;
import dagger.Provides;


@Module(includes = {ApplicationContextModule.class})
public class DataModule {

    @Provides
    @ApplicationScope
    PrefUtils prefUtils(Context context) {
        return new PrefUtils(context);
    }
}
