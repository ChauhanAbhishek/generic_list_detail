package com.example.listdetailapplication.list;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.listdetailapplication.CommonApplication;
import com.example.listdetailapplication.CommonRepository;
import com.example.listdetailapplication.models.Movie;
import com.example.listdetailapplication.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    CommonRepository mCommonRepository;
    public static final String QUERY_EXHAUSTED = "No more results.";
    private static final String TAG = "RecipeListViewModel";
    private MediatorLiveData<Resource<List<Movie>>> movies = new MediatorLiveData<>();

    private MutableLiveData<Movie> openThisMovie = new MediatorLiveData<>();
    private MutableLiveData<Integer> updatePosition = new MutableLiveData<>();


    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private int pageNumber;
    private String query;
    private boolean cancelRequest;
    private long requestStartTime;
    private boolean isColdStart=true;

    @Inject
    public ListViewModel(CommonRepository repository)
    {
        Log.d("check not null " , repository + " ls");
        mCommonRepository = repository;
    }

    public void init()
    {
        if(isColdStart)
        {
            searchRecipesApi("friends",1, true);
            isColdStart=false;
        }
    }

    public LiveData<List<Movie>> getAllMovies()
    {
        return mCommonRepository.getAllMovies();
    }

    public MutableLiveData<Integer> getUpdatePosition() {
        return updatePosition;
    }



    public void bookmarkMovie(Movie movie, int position)
    {
        mCommonRepository.bookmarkMovie(movie).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer response) {
                        Log.d("cnrr",response+"");
                        if(response>0)
                        {
                           // movie.setBookmarked(movie.getBookmarked()==0?1:0);
                            //updatePosition.setValue(position);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("cnrr",e+"");

                    }
                });
    }

    public LiveData<List<Movie>> getAllBoomkarkedMovies() {
        return mCommonRepository.getAllBoomkarkedMovies();
    }


    public void openMovieDetail(Movie movie)
    {
        openThisMovie.setValue(movie);
    }

    public LiveData<Movie> getOpenThisMovie() {
        return openThisMovie;
    }

    public void setOpenThisMovie(MutableLiveData<Movie> openThisMovie) {
        this.openThisMovie = openThisMovie;
    }

    public LiveData<Resource<List<Movie>>> getMovies(){
        return movies;
    }

    public int getPageNumber(){
        return pageNumber;
    }

    public void searchRecipesApi(String query, int pageNumber, boolean isColdStart){
        if(!isPerformingQuery){
            if(pageNumber == 0){
                pageNumber = 1;
            }
            this.pageNumber = pageNumber;
            this.query = query;
            isQueryExhausted = false;
            executeSearch(isColdStart);

        }
    }

    public void searchNextPage(){
        if(!isQueryExhausted && !isPerformingQuery){
            pageNumber++;
            executeSearch(false);
        }
    }


    public void cancelSearchRequest(){
        if(isPerformingQuery){
            cancelRequest = true;
            isPerformingQuery = false;
            pageNumber = 1;
        }
    }

    LiveData<Resource<List<Movie>>> repositorySource ;


    private void executeSearch(boolean isColdStart){
        requestStartTime = System.currentTimeMillis();
        cancelRequest = false;
        isPerformingQuery = true;
        if(repositorySource!=null)
        {
            movies.removeSource(repositorySource);
        }
        repositorySource = mCommonRepository.executeSearch(query, pageNumber,isColdStart);
        movies.addSource(repositorySource, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                if(!cancelRequest){
                    if(listResource != null){
                        if(listResource.status == Resource.Status.SUCCESS){
                            Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime) / 1000 + " seconds.");
                            Log.d(TAG, "onChanged: page number: " + pageNumber);
                            Log.d(TAG, "onChanged: " + listResource.data);

                            isPerformingQuery = false;
                            if(listResource.data != null){
                                if(listResource.data.size() == 0 ){
                                    Log.d(TAG, "onChanged: query is exhausted...");
                                    movies.setValue(
                                            new Resource<List<Movie>>(
                                                    Resource.Status.ERROR,
                                                    listResource.data,
                                                    QUERY_EXHAUSTED
                                            )
                                    );
                                    isQueryExhausted = true;
                                }
                            }
                            //movies.removeSource(repositorySource);
                        }
                        else if(listResource.status == Resource.Status.ERROR){
                            Log.d(TAG, "onChanged: REQUEST TIME: " + (System.currentTimeMillis() - requestStartTime) / 1000 + " seconds.");
                            isPerformingQuery = false;
                            if(listResource.message.equals(QUERY_EXHAUSTED)){
                                isQueryExhausted = true;
                            }
                            //movies.removeSource(repositorySource);
                        }
                        movies.setValue(listResource);
                    }
                    else{
                        //movies.removeSource(repositorySource);
                    }
                }
                else{
                    //movies.removeSource(repositorySource);
                }
            }
        });
    }





}
