package com.example.listdetailapplication;

import android.util.Log;

import com.example.listdetailapplication.database.MovieDao;
import com.example.listdetailapplication.list.ListResponseApi;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CommonRepository {

    ApiService mApiService;
    MovieDao mNoteDao;

    public  CommonRepository(ApiService apiService, MovieDao noteDao)
    {
        Log.d("check not", apiService + " n");
        Log.d("check not", noteDao + " n");
        mApiService = apiService;
        mNoteDao = noteDao;
    }

    public void checkReq()
    {
        Single<Response<ListResponseApi>> travelResponse;
        travelResponse = mApiService.searchMovies("960d58e9","friend","1");
        travelResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Response<ListResponseApi>>() {
                    @Override
                    public void onSuccess(Response<ListResponseApi> response) {
                        Log.d("cnrrr",response.body().getSearch().toString());
                        if(response.isSuccessful()) {
//                            bulkInsert(response.body().getLocations());
//                            customerName.setValue(response.body().getCustName());
                        } else {
                            //todo handle errors
                           // toastMessage.setValue(new Event<>("You are offline. Enjoy your cache."));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        //todo handle errors
                        Log.d("cnrr",e.toString());
//                        viewModelNonUIChangesListener.errorOccurred();
                       // toastMessage.setValue(new Event<>("You are offline. Enjoy your cache."));

                    }
                });
    }


}
