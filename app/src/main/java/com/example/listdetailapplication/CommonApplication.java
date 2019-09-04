package com.example.listdetailapplication;

import android.app.Activity;
import android.app.Application;

import com.example.listdetailapplication.di.component.ApplicationComponent;
import com.example.listdetailapplication.di.component.DaggerApplicationComponent;
import com.example.listdetailapplication.di.component.ListActivityComponent;
import com.example.listdetailapplication.di.modules.ApplicationContextModule;

public class CommonApplication extends Application {
    private static CommonApplication instance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
            applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
    }

    public static CommonApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static CommonApplication get(Activity activity) {
        return (CommonApplication) activity.getApplication();
    }

    public ApplicationComponent component() {
        return applicationComponent;
    }
}
