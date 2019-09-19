package com.example.listdetailapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.listdetailapplication.models.Movie;


@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "movie_db";

    public abstract MovieDao getMovieDao();
}
