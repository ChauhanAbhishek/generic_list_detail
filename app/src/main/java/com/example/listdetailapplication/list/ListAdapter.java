package com.example.listdetailapplication.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdetailapplication.ListActivity;
import com.example.listdetailapplication.R;
import com.example.listdetailapplication.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnMovieListener {

    private ListActivity activity;
    private Picasso picasso;
    private List<Movie> movieList;

    private static final int MOVIE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    @Inject
    public ListAdapter(ListActivity activity,Picasso picasso) {
        this.activity = activity;
        this.picasso = picasso;
    }

    public void setItemList(List<Movie> movieList)
    {
        this.movieList=movieList;
    }

    @Override
    public void onMovieBookmarked(int position) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i){

            case MOVIE_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_movie_list_item, viewGroup, false);
                return new MovieViewHolder(view, this, picasso);
            }

            case LOADING_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }

            case EXHAUSTED_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_exhausted, viewGroup, false);
                return new SearchExhaustedViewHolder(view);
            }

            default:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_movie_list_item, viewGroup, false);
                return new MovieViewHolder(view, this, picasso);
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

        if(movieList.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        }
        else if(movieList.get(position).getTitle().equals("LOADING...")){
            return EXHAUSTED_TYPE;
        }
        else{
            return MOVIE_TYPE;
        }

    }

    @Override
    public int getItemCount()
    {
        return movieList.size();
    }

    // display loading during search request
    public void displayOnlyLoading(){
        clearRecipesList();
        Movie recipe = new Movie();
        recipe.setTitle("LOADING...");
        movieList.add(recipe);
        notifyDataSetChanged();
    }

    private void clearRecipesList(){
        if(movieList == null){
            movieList = new ArrayList<>();
        }
        else{
            movieList.clear();
        }
        notifyDataSetChanged();
    }

    public void setQueryExhausted(){
        hideLoading();
        Movie exhaustedMovie = new Movie();
        exhaustedMovie.setTitle("EXHAUSTED...");
        movieList.add(exhaustedMovie);
        notifyDataSetChanged();
    }

    public void hideLoading(){
        if(isLoading()){
            if(movieList.get(0).getTitle().equals("LOADING...")){
                movieList.remove(0);
            }
            else if(movieList.get(movieList.size() - 1).getTitle().equals("LOADING...")){
                movieList.remove(movieList.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    // pagination loading
    public void displayLoading(){
        if(movieList == null){
            movieList = new ArrayList<>();
        }
        if(!isLoading()){
            Movie movie = new Movie();
            movie.setTitle("LOADING...");
            movieList.add(movie);
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if(movieList != null){
            if(movieList.size() > 0){
                if(movieList.get(movieList.size() - 1).getTitle().equals("LOADING...")){
                    return true;
                }
            }
        }
        return false;
    }

}
