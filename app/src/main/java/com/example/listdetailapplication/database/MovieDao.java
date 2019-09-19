package com.example.listdetailapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.listdetailapplication.models.Movie;

import java.util.List;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {


    @Insert(onConflict = IGNORE)
    long[] insertMovies(Movie... recipe);

    @Insert(onConflict = REPLACE)
    void insertMovie(Movie recipe);

    @Query("Delete from movies")
    int deleteAllMovies();

    @Query("SELECT * FROM movies WHERE imdb_id = :movieId")
    LiveData<Movie> getMovie(String movieId);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

}














