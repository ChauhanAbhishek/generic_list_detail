package com.example.listdetailapplication.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.R;
import com.example.listdetailapplication.databinding.LayoutMovieListItemBinding;
import com.example.listdetailapplication.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnMovieListener {

    private Context context;
    private Picasso picasso;
    private List<Movie> movieList;
    ListViewModel viewModel;

    private static final int MOVIE_TYPE = 1;

    @Inject
    public BookmarkAdapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    public void setItemList(List<Movie> movieList)
    {
        this.movieList=movieList;
        notifyDataSetChanged();
    }

    public void setViewModel(ListViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    @Override
    public void onMovieClicked(int position) {
        viewModel.openMovieDetail(movieList.get(position));
    }

    @Override
    public void onMovieBookmarked(Movie movie,int position) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i){

            case MOVIE_TYPE:{
                LayoutMovieListItemBinding layoutMovieListItemBinding = DataBindingUtil
                        .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_movie_list_item,
                                viewGroup, false);
                return new MovieViewHolder(layoutMovieListItemBinding, this, picasso);
            }



            default:{
                LayoutMovieListItemBinding layoutMovieListItemBinding = DataBindingUtil
                        .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.layout_movie_list_item,
                                viewGroup, false);
                return new MovieViewHolder(layoutMovieListItemBinding, this, picasso);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if(itemViewType == MOVIE_TYPE){
            ((MovieViewHolder)viewHolder).onBind(movieList.get(i));
        }
    }

    @Override
    public int getItemViewType(int position) {

        return MOVIE_TYPE;
    }

    @Override
    public int getItemCount()
    {
        return movieList.size();
    }




}
