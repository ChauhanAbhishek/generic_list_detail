package com.example.listdetailapplication.di.modules;



import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.di.annotations.ListActivityScope;
import com.example.listdetailapplication.list.OnMovieListener;

import dagger.Module;
import dagger.Provides;

@Module
public class ListActivityModule {

    private final OnMovieListener onMovieListener;

    public ListActivityModule(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @Provides
    @ListActivityScope
    public OnMovieListener listActivity() {
        return onMovieListener;
    }
}
