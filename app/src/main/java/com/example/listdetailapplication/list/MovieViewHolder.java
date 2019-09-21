package com.example.listdetailapplication.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdetailapplication.R;
import com.example.listdetailapplication.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView imdb_id, title, year,type,imdb;
    ImageView poster;
    OnMovieListener onMovieListener;
    Picasso picasso;

    public MovieViewHolder(@NonNull View itemView,
                            OnMovieListener onMovieListener, Picasso picasso) {
        super(itemView);
        this.onMovieListener = onMovieListener;
        this.picasso = picasso;


        imdb_id = itemView.findViewById(R.id.imdb_id);
        title = itemView.findViewById(R.id.title);
        year = itemView.findViewById(R.id.year);
        type = itemView.findViewById(R.id.type);
        imdb = itemView.findViewById(R.id.imdb);
        poster = itemView.findViewById(R.id.poster);


        itemView.setOnClickListener(this);
    }

    public void onBind(Movie movie){
        imdb_id.setText(movie.getImdbID());
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        type.setText(movie.getType());
        imdb.setText(movie.getImdb());

    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieBookmarked(getAdapterPosition());
    }
}




