package com.example.listdetailapplication.list;

import com.example.listdetailapplication.models.Movie;

public interface OnMovieListener {

    void onMovieClicked(int position);
    void onMovieBookmarked(Movie movie,int position);
}
