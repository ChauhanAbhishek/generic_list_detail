package com.example.listdetailapplication.detail;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdetailapplication.R;
import com.example.listdetailapplication.databinding.LayoutBookmarkItemBinding;
import com.example.listdetailapplication.databinding.LayoutMovieListItemBinding;
import com.example.listdetailapplication.list.OnMovieListener;
import com.example.listdetailapplication.models.Movie;
import com.squareup.picasso.Picasso;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {

    LayoutBookmarkItemBinding layoutBookmarkItemBinding;
    OnMovieListener onMovieListener;
    Picasso picasso;

    public BookmarkViewHolder(@NonNull LayoutBookmarkItemBinding binding,
                           OnMovieListener onMovieListener, Picasso picasso) {
        super(binding.getRoot());
        this.onMovieListener = onMovieListener;
        this.picasso = picasso;
        layoutBookmarkItemBinding = binding;
    }

    public void onBind(Movie movie){
        picasso.load(movie.getPoster()).into(layoutBookmarkItemBinding.poster);
        if(movie.getBookmarked()==0)
        {
            layoutBookmarkItemBinding.bookmark.setImageResource(R.drawable.bookmark_unselected);
        }
        else
        {
            layoutBookmarkItemBinding.bookmark.setImageResource(R.drawable.bookmark_selected);
        }

        layoutBookmarkItemBinding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(movie.getBookmarked()==0)
//                {
//                    on
//                }
//                else
//                {
//
//                }

                onMovieListener.onMovieBookmarked(movie,getAdapterPosition());
            }
        });

        layoutBookmarkItemBinding.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieListener.onMovieClicked(getAdapterPosition());
            }
        });

    }

}
