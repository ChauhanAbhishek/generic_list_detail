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

    @Query("SELECT * FROM movies where bookmarked = 1")
    LiveData<List<Movie>> getAllBookmarkedMovies();

    @Query("UPDATE movies SET meta_score = :metaScore, imdb_rating =:imdbRating,imdb_votes =  :imdbVotes, dvd = :dvd, box_office = :boxOffice, production = :production, website = :website , timestamp = :timestamp " +
            "WHERE imdb_id = :imdbId")
    int updateRecipe(String imdbId, String metaScore, String imdbRating, String imdbVotes, String dvd, String boxOffice, String production, String website,int timestamp);

    @Query("UPDATE movies SET bookmarked = :bookmarked " +
            "WHERE imdb_id = :imdbId")
    Single<Integer> updateBookmark(String imdbId, int bookmarked);

}














