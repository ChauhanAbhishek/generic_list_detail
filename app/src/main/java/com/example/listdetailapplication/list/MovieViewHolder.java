package com.example.listdetailapplication.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdetailapplication.R;
import com.example.listdetailapplication.databinding.LayoutMovieListItemBinding;
import com.example.listdetailapplication.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    LayoutMovieListItemBinding layoutMovieListItemBinding;
    OnMovieListener onMovieListener;
    Picasso picasso;

    public MovieViewHolder(@NonNull LayoutMovieListItemBinding binding,
                            OnMovieListener onMovieListener, Picasso picasso) {
        super(binding.getRoot());
        this.onMovieListener = onMovieListener;
        this.picasso = picasso;
        layoutMovieListItemBinding = binding;
    }

    public void onBind(Movie movie){
        layoutMovieListItemBinding.setMovieItem(movie);
        picasso.load(movie.getPoster()).into(layoutMovieListItemBinding.poster);

    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClicked(getAdapterPosition());
    }
}




