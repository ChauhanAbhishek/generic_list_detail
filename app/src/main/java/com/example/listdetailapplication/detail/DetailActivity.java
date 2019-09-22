package com.example.listdetailapplication.detail;

import android.os.Bundle;

import com.example.listdetailapplication.CommonApplication;
import com.example.listdetailapplication.ViewModelProviderFactory;
import com.example.listdetailapplication.databinding.ActivityDetailBinding;
import com.example.listdetailapplication.databinding.ActivityMainBinding;
import com.example.listdetailapplication.di.component.DaggerDetailActivityComponent;
import com.example.listdetailapplication.di.component.DaggerListActivityComponent;
import com.example.listdetailapplication.di.component.DetailActivityComponent;
import com.example.listdetailapplication.di.component.ListActivityComponent;
import com.example.listdetailapplication.di.modules.DetailActivityModule;
import com.example.listdetailapplication.di.modules.ListActivityModule;
import com.example.listdetailapplication.list.ListViewModel;
import com.example.listdetailapplication.models.Movie;
import com.example.listdetailapplication.utils.Resource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.listdetailapplication.R;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

    @Inject
    ViewModelProviderFactory providerFactory;

    DetailActivityViewModel detailActivityViewModel;

    ProgressBar progressBar;
    private ActivityDetailBinding activityDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        activityDetailBinding.setLifecycleOwner(this);

        progressBar = activityDetailBinding.contentContainer.progressBar;


                DetailActivityComponent component = DaggerDetailActivityComponent.builder()
                .detailActivityModule(new DetailActivityModule())
                .applicationComponent(CommonApplication.get(this).component())
                .build();

        component.injectHomeActivity(this);

        detailActivityViewModel = ViewModelProviders.of(this, providerFactory).get(DetailActivityViewModel.class);
        detailActivityViewModel.init();

        Log.d("cnrd",detailActivityViewModel+"d");

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("movie")){
            Movie movie = (Movie)getIntent().getSerializableExtra("movie");
            subscribeObservers(movie.getImdbID());
        }
    }

    public void subscribeObservers(String imdbId)
    {
        detailActivityViewModel.searchMovieById(imdbId).observe(this, new Observer<Resource<Movie>>() {
        @Override
        public void onChanged(@Nullable Resource<Movie> recipeResource) {
            Log.d("cnru","update " + recipeResource);

            if(recipeResource != null){
                if(recipeResource.data != null){
                    switch (recipeResource.status){

                        case LOADING:{
                            showProgressBar(true);
                            break;
                        }

                        case ERROR:{
                          //  Log.e(TAG, "onChanged: status: ERROR, Recipe: " + recipeResource.data.getTitle() );
                         //   Log.e(TAG, "onChanged: ERROR message: " + recipeResource.message );
                            //showParent();
                            showProgressBar(false);
                            setMovieProperties(recipeResource.data);
                            break;
                        }

                        case SUCCESS:{
                           // Log.d(TAG, "onChanged: cache has been refreshed.");
                           // Log.d(TAG, "onChanged: status: SUCCESS, Recipe: " + recipeResource.data.getTitle());
                            //showParent();
                            showProgressBar(false);
                            setMovieProperties(recipeResource.data);
                            break;
                        }
                    }
                }
            }
        }
    });
    }

    public void showProgressBar(boolean visibility){
        progressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    public void setMovieProperties(Movie movie)
    {
        Log.d("cnru","update movie prop");
        activityDetailBinding.contentContainer.imdbId.setText(movie.getImdbID());
        activityDetailBinding.contentContainer.title.setText(movie.getTitle());
        activityDetailBinding.contentContainer.year.setText(movie.getYear());
        activityDetailBinding.contentContainer.imdb.setText(movie.getImdb());
        activityDetailBinding.contentContainer.type.setText(movie.getType());

        if(movie.getImdbRating()!=null)
        {
            activityDetailBinding.contentContainer.metaScore.setText(movie.getMetaScore());
            activityDetailBinding.contentContainer.imdbRating.setText(movie.getImdbRating());
            activityDetailBinding.contentContainer.imdbVotes.setText(movie.getImdbVotes());
            activityDetailBinding.contentContainer.dvd.setText(movie.getDvd());
            activityDetailBinding.contentContainer.boxOffice.setText(movie.getBoxOffice());
            activityDetailBinding.contentContainer.production.setText(movie.getProduction());
            activityDetailBinding.contentContainer.website.setText(movie.getWebsite());
        }

    }

}
