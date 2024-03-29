package com.example.listdetailapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.listdetailapplication.database.MovieDao;
import com.example.listdetailapplication.detail.DetailResponseApi;
import com.example.listdetailapplication.list.ListResponseApi;
import com.example.listdetailapplication.list.Search;
import com.example.listdetailapplication.models.Movie;
import com.example.listdetailapplication.utils.ApiResponse;
import com.example.listdetailapplication.utils.AppExecutors;
import com.example.listdetailapplication.utils.NetworkBoundResource;
import com.example.listdetailapplication.utils.Resource;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
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

    public LiveData<Resource<List<Movie>>> executeSearch(String query, int pageNumber, boolean isColdStart)
    {
        return new NetworkBoundResource<List<Movie>, ListResponseApi>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull ListResponseApi item) {

                if(item.getSearch() != null){ // recipe list will be null if the api key is expired
//                    Log.d(TAG, "saveCallResult: recipe response: " + item.toString());

//                    Search[] sea = (Search[])item.getSearch().toArray();

                    ArrayList<Movie> movieArrayList = new ArrayList<>();


                    for(int i=0;i<item.getSearch().size();i++) {
                        Search search = item.getSearch().get(i);
                        Movie movie = new Movie();
                        movie.setTitle(search.getTitle());
                        movie.setImdbID(search.getImdbID());
                        movie.setPoster(search.getPoster());
                        movie.setType(search.getType());
                        movie.setYear(search.getYear());
                        movie.setFreshData(1);

                        movieArrayList.add(movie);
//                        int insertStatus = mNoteDao.insertMovie(movie);
//
//                        if(insertStatus==-1)
//                        {
//                            mNoteDao.updateBookmarkToFreshData(movie.getImdbID());
//                        }

                    }


                    int index=0;
                    for(long rowid: mNoteDao.insertMovies(movieArrayList)){
                        if(rowid==-1)
                        {
                            mNoteDao.updateBookmarkToFreshData(movieArrayList.get(index).getImdbID());
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldUpdateData() {
                return pageNumber==1;
            }

            @Override
            protected void updateData() {
                mNoteDao.updateBookmarkToStaleData();
            }

            @Override
            protected boolean shouldClearData() {
                Log.d("cnrl",isColdStart + " " + pageNumber);
                return  isColdStart || pageNumber==1;
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
                        .onErrorReturn(new Function<Throwable, Response<ListResponseApi>>() {
                            @Override
                            public Response<ListResponseApi> apply(Throwable throwable) throws Exception {
                                return Response.error(404, new ResponseBody() {
                                    @Override
                                    public MediaType contentType() {
                                        return null;
                                    }

                                    @Override
                                    public long contentLength() {
                                        return 0;
                                    }

                                    @Override
                                    public BufferedSource source() {
                                        return null;
                                    }
                                });
                            }
                        })
                        .map(new Function<Response<ListResponseApi>,  ApiResponse<ListResponseApi>>() {
                            @Override
                            public ApiResponse<ListResponseApi> apply(Response<ListResponseApi> response) throws Exception {
                                if(response.isSuccessful())
                                {
                                    return apiResponse.create(response);
                                }
                                else
                                {
                                    return apiResponse.create(new Throwable(""));
                                }

                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .toFlowable());
            }

            @Override
            protected void clearData() {
                Log.d("cnrl","clearing data");
                mNoteDao.deleteAllMovies();
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Movie>> searchMovieById(String movieId)
    {
        return new NetworkBoundResource<Movie, DetailResponseApi>(AppExecutors.getInstance()){
            @Override
            protected void saveCallResult(@NonNull DetailResponseApi item) {

                // will be null if API key is expired
                if(item.getImdbRating() != null){
                   // item.get().setTimestamp((int)(System.currentTimeMillis() / 1000));
                    Log.d("cnru","update");

                    int u = mNoteDao.updateRecipe(item.getImdbID(),item.getMetascore(),item.getImdbRating(),item.getImdbVotes(),item.getDVD(),item.getBoxOffice(),item.getProduction(),item.getWebsite(),(int)(System.currentTimeMillis() / 1000));

                    Log.d("cnru","update " + u);


                }
            }

            @Override
            protected boolean shouldUpdateData() {
                return false;
            }

            @Override
            protected void updateData() {
            }

            @Override
            protected boolean shouldFetch(@Nullable Movie data) {
                if(data.getImdbRating()!=null)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            protected void clearData() {

            }

            public void bookmarkMovie(Movie movie)
            {

            }

            @Override
            protected boolean shouldClearData() {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Movie> loadFromDb() {
                return mNoteDao.getMovie(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DetailResponseApi>> createCall() {
                final ApiResponse apiResponse = new ApiResponse();
                Single<Response<DetailResponseApi>> movieResponse;
                movieResponse = mApiService.getMovieDetail("960d58e9",movieId);
                return LiveDataReactiveStreams.fromPublisher(movieResponse.subscribeOn(Schedulers.io())
                        .onErrorReturn(new Function<Throwable, Response<DetailResponseApi>>() {
                            @Override
                            public Response<DetailResponseApi> apply(Throwable throwable) throws Exception {
                                return Response.error(404, new ResponseBody() {
                                    @Override
                                    public MediaType contentType() {
                                        return null;
                                    }

                                    @Override
                                    public long contentLength() {
                                        return 0;
                                    }

                                    @Override
                                    public BufferedSource source() {
                                        return null;
                                    }
                                });
                            }
                        })
                        .map(new Function<Response<DetailResponseApi>,  ApiResponse<DetailResponseApi>>() {
                            @Override
                            public ApiResponse<DetailResponseApi> apply(Response<DetailResponseApi> response) throws Exception {
                                if(response.isSuccessful())
                                {
                                    return apiResponse.create(response);
                                }
                                else
                                {
                                    return apiResponse.create(new Throwable(""));
                                }

                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .toFlowable());
            }
        }.getAsLiveData();
    }

    public LiveData<List<Movie>> getAllBoomkarkedMovies()
    {
        return mNoteDao.getAllBookmarkedMovies();
    }

    public LiveData<List<Movie>> getAllMovies()
    {
        return mNoteDao.getAllMovies();
    }

    public Single<Integer> bookmarkMovie(Movie movie)
    {
        Log.d("cnrr","bookmark");

        return mNoteDao.updateBookmark(movie.getImdbID(),movie.getBookmarked()==0?1:0);
    }


}



