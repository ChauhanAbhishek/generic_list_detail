package com.example.listdetailapplication.di.modules;



import android.content.Context;

import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.di.annotations.ListActivityScope;
import com.example.listdetailapplication.list.OnMovieListener;

import dagger.Module;
import dagger.Provides;

@Module
public class ListActivityModule {

    private final Context context;

    public ListActivityModule(Context context) {
        this.context = context;
    }

    @Provides
    @ListActivityScope
    public Context listActivity() {
        return context;
    }
}
