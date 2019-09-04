package com.example.listdetailapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.listdetailapplication.di.component.DaggerListActivityComponent;
import com.example.listdetailapplication.di.component.ListActivityComponent;
import com.example.listdetailapplication.di.modules.ListActivityModule;
import com.example.listdetailapplication.list.ListAdapter;
import com.example.listdetailapplication.list.ListViewModel;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity {

    @Inject
    ListAdapter listAdapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    ListViewModel listViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListActivityComponent component = DaggerListActivityComponent.builder()
                .listActivityModule(new ListActivityModule(this))
                .applicationComponent(CommonApplication.get(this).component())
                .build();

        component.injectHomeActivity(this);

        listViewModel = ViewModelProviders.of(this, providerFactory).get(ListViewModel.class);


        Log.d("check not null " , listAdapter + " ls");
        Log.d("check not null " , providerFactory + " ls");
    }
}
