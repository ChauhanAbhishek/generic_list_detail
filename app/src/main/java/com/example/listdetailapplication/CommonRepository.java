package com.example.listdetailapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.listdetailapplication.database.MovieDao;
import com.example.listdetailapplication.list.ListResponseApi;
import com.example.listdetailapplication.list.Search;
import com.example.listdetailapplication.models.Movie;
import com.example.listdetailapplication.utils.ApiResponse;
import com.example.listdetailapplication.utils.AppExecutors;
import com.example.listdetailapplication.utils.NetworkBoundResource;
import com.example.listdetailapplication.utils.Resource;
import androidx.lifecycle.LiveDataReactiveStreams;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CommonRepository {

    ApiService mApiService;
    MovieDao mNoteDao;

    public  CommonRepository(ApiService apiService, MovieDao movieDao)
    {
        Log.d("check not", apiService + " n");
        Log.d("check not", movieDao + " n");
        mApiService = apiService;
        mNoteDao = movieDao;
    }

    public LiveData<Resource<List<Movie>>> executeSearch(String query, int pageNumber)
    {
        return new NetworkBoundResource<List<Movie>, ListResponseApi>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull ListResponseApi item) {

                if(item.getSearch() != null){ // recipe list will be null if the api key is expired
//                    Log.d(TAG, "saveCallResult: recipe response: " + item.toString());

//                    Search[] sea = (Search[])item.getSearch().toArray();

                    for(int i=0;i<item.getSearch().size();i++) {
                        Search search = item.getSearch().get(i);
                        Movie movie = new Movie();
                        movie.setTitle(search.getTitle());
                        movie.setImdbID(search.getImdbID());
                        movie.setPoster(search.getPoster());
                        movie.setType(search.getType());
                        movie.setYear(search.getYear());

                        mNoteDao.insertMovie(movie);

                    }



//                    int index = 0;
//                    for(long rowid: mNoteDao.insertMovies((Movie[]) (item.getSearch().toArray(movies)))){
//                        if(rowid == -1){
                           // Log.d(TAG, "saveCallResult: CONFLICT... This recipe is already in the cache");
                            // if the recipe already exists... I don't want to set the ingredients or timestamp b/c
                            // they will be erased
//                            recipeDao.updateRecipe(
//                                    recipes[index].getRecipe_id(),
//                                    recipes[index].getTitle(),
//                                    recipes[index].getPublisher(),
//                                    recipes[index].getImage_url(),
//                                    recipes[index].getSocial_rank()
//                            );
//                        }
//                        index++;
                    //}
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return mNoteDao.getAllMovies();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ListResponseApi>> createCall() {
                final ApiResponse apiResponse = new ApiResponse();
                Single<Response<ListResponseApi>> travelResponse;
                travelResponse = mApiService.searchMovies("960d58e9",query,String.valueOf(pageNumber));
                return LiveDataReactiveStreams.fromPublisher(travelResponse.subscribeOn(Schedulers.io())
                        .map(new Function<Response<ListResponseApi>,  ApiResponse<ListResponseApi>>() {
                            @Override
                            public ApiResponse<ListResponseApi> apply(Response<ListResponseApi> response) throws Exception {
                                return apiResponse.create(response);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .toFlowable());
            }
        }.getAsLiveData();
    }


}



