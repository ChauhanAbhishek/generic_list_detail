package com.example.listdetailapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.listdetailapplication.databinding.ActivityMainBinding;
import com.example.listdetailapplication.detail.DetailActivity;
import com.example.listdetailapplication.di.component.DaggerListActivityComponent;
import com.example.listdetailapplication.di.component.ListActivityComponent;
import com.example.listdetailapplication.di.modules.ListActivityModule;
import com.example.listdetailapplication.list.BookmarkAdapter;
import com.example.listdetailapplication.list.ListAdapter;
import com.example.listdetailapplication.list.ListViewModel;
import com.example.listdetailapplication.list.OnMovieListener;
import com.example.listdetailapplication.models.Movie;
import com.example.listdetailapplication.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.multibindings.ElementsIntoSet;

import static com.example.listdetailapplication.list.ListViewModel.QUERY_EXHAUSTED;
import static com.example.listdetailapplication.utils.Resource.Status.LOADING;

public class ListActivity extends AppCompatActivity  {


    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ListAdapter listAdapter;

    @Inject
    BookmarkAdapter bookmarkAdapter;

    ListViewModel listViewModel ;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private RecyclerView mBookmarkRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding  activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);

        mRecyclerView = activityMainBinding.content.recipeList;
        mBookmarkRecyclerView = activityMainBinding.content.bookmarkList;
        mSearchView = activityMainBinding.searchView;
        activityMainBinding.toolbar.setTitle("Movies");

        ListActivityComponent component = DaggerListActivityComponent.builder()
                .listActivityModule(new ListActivityModule(this))
                .applicationComponent(CommonApplication.get(this).component())
                .build();

        component.injectHomeActivity(this);

        listViewModel = ViewModelProviders.of(this, providerFactory).get(ListViewModel.class);
        listViewModel.init();
        //listViewModel.checkReq();
        initRecyclerView();
        initBookmarkRecyclerView();
        initSearchView();
        subscribeObservers();
      //  setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

    }


    private void initRecyclerView(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!mRecyclerView.canScrollVertically(1)){
                    listViewModel.searchNextPage();
                }
            }
        });

        listAdapter.setItemList(new ArrayList<>());
        listAdapter.setViewModel(listViewModel);
        mRecyclerView.setAdapter(listAdapter);
    }

    private void initBookmarkRecyclerView(){

        mBookmarkRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        bookmarkAdapter.setItemList(new ArrayList<>());
        bookmarkAdapter.setViewModel(listViewModel);
        mBookmarkRecyclerView.setAdapter(bookmarkAdapter);
    }

    private void initSearchView(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchMovieApi(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void searchMovieApi(String s)
    {
        mRecyclerView.smoothScrollToPosition(0);
        listViewModel.searchRecipesApi(s, 1,false);
        mSearchView.clearFocus();
    }

    public static String TAG = "List activity";

    private void subscribeObservers(){
        listViewModel.getMovies().observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                if(listResource != null){
                    Log.d("cnrr", "onChanged: status: " + listResource.status);

                    if(listResource.data != null){
                        switch (listResource.status){
                            case LOADING:{
                                if(listViewModel.getPageNumber() > 1){
                                    listAdapter.displayLoading();
                                }
                                else{
                                    listAdapter.displayOnlyLoading();
                                }
                                break;
                            }

                            case ERROR:{
                                Log.e(TAG, "onChanged: cannot refresh the cache." );
                                Log.e(TAG, "onChanged: ERROR message: " + listResource.message );
                                Log.e(TAG, "onChanged: status: ERROR, #recipes: " + listResource.data.size());
                                listAdapter.hideLoading();
                                listAdapter.setItemList(listResource.data);
                                Toast.makeText(ListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();

                                if(listResource.message.equals(QUERY_EXHAUSTED)){
                                    listAdapter.setQueryExhausted();
                                }
                                break;
                            }

                            case SUCCESS:{
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, #Recipes: " + listResource.data.size());
                                listAdapter.hideLoading();
                                listAdapter.setItemList(listResource.data);
                                break;
                            }
                        }
                    }
                }
            }
        });

        listViewModel.getAllBoomkarkedMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                if(movieList != null){
                    if(movieList.size()>0)
                    {
                        mBookmarkRecyclerView.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mBookmarkRecyclerView.setVisibility(View.GONE);
                    }
                    bookmarkAdapter.setItemList(movieList);
                }
            }
        });

        listViewModel.getOpenThisMovie().observe(this,new Observer<Movie>()
                {
                    @Override
                    public void onChanged(@Nullable Movie movie) {
                        Intent i = new Intent(ListActivity.this, DetailActivity.class);
                        i.putExtra("movie", movie);
                        startActivity(i);
                    }
                });

            listViewModel.getUpdatePosition().observe(this,new Observer<Integer>()
        {
            @Override
            public void onChanged(@Nullable Integer integer) {
                listAdapter.notifyItemChanged(integer);
            }
        });

            listViewModel.getAllMovies().observe(this,new Observer<List<Movie>>()
            {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                   Log.d("cnrx","change fir se hua");
                }
            });

    }
}

