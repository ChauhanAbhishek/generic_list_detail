package com.example.listdetailapplication.di.modules;



import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.di.annotations.ListActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ListActivityModule {

    private final ListActivity listActivity;

    public ListActivityModule(ListActivity listActivity) {
        this.listActivity = listActivity;
    }

    @Provides
    @ListActivityScope
    public ListActivity listActivity() {
        return listActivity;
    }
}
