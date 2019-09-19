package com.example.listdetailapplication.di.modules;




import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.listdetailapplication.ApiService;
import com.example.listdetailapplication.CommonRepository;
import com.example.listdetailapplication.database.MovieDao;
import com.example.listdetailapplication.database.MovieDatabase;

import com.example.listdetailapplication.di.annotations.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.listdetailapplication.database.MovieDatabase.DATABASE_NAME;

@Module
public class RepositoryModule {

    @Provides
    @ApplicationScope
    public CommonRepository repositoryModule(ApiService apiService, MovieDao noteDao)
    {
        return new CommonRepository(apiService,noteDao);
    }

    @ApplicationScope
    @Provides
    static MovieDatabase provideNoteDatabase(Context context){
        return Room.databaseBuilder(
                context,
                MovieDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @ApplicationScope
    @Provides
    static MovieDao provideNoteDao(MovieDatabase movieDatabase){
        return movieDatabase.getMovieDao();
    }
}
