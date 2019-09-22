package com.example.listdetailapplication.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.listdetailapplication.ViewModelProviderFactory;
import com.example.listdetailapplication.detail.DetailActivityViewModel;
import com.example.listdetailapplication.list.ListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    public abstract ViewModel bindListViewModel(ListViewModel noteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailActivityViewModel.class)
    public abstract ViewModel bindDetailActivityViewModel(DetailActivityViewModel detailActivityViewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(NotesListViewModel.class)
//    public abstract ViewModel bindNotesListViewModel(NotesListViewModel noteViewModel);

}







