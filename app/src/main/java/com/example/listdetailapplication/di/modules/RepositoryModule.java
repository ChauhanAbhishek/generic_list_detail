package com.example.listdetailapplication.di.modules;




import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.listdetailapplication.ApiService;
import com.example.listdetailapplication.CommonRepository;
import com.example.listdetailapplication.database.NoteDao;
import com.example.listdetailapplication.database.NoteDatabase;
import com.example.listdetailapplication.di.annotations.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.listdetailapplication.database.NoteDatabase.DATABASE_NAME;

@Module
public class RepositoryModule {

    @Provides
    @ApplicationScope
    public CommonRepository repositoryModule(ApiService apiService,NoteDao noteDao)
    {
        return new CommonRepository(apiService,noteDao);
    }

    @ApplicationScope
    @Provides
    static NoteDatabase provideNoteDatabase(Context context){
        return Room.databaseBuilder(
                context,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @ApplicationScope
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }
}
