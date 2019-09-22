package com.example.listdetailapplication.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listdetailapplication.CommonRepository;
import com.example.listdetailapplication.models.Movie;
import com.example.listdetailapplication.utils.Resource;

import javax.inject.Inject;

public class DetailActivityViewModel extends ViewModel {

    CommonRepository commonRepository;
    boolean isDataSet;

    @Inject
    public DetailActivityViewModel(CommonRepository commonRepository) {
        this.commonRepository=commonRepository;
        Log.d("cnrd",commonRepository+"c");
    }

    public void init()
    {
        if(!isDataSet)
        {
            isDataSet=true;
        }
    }

    public LiveData<Resource<Movie>> searchMovieById(String movieId){
        return commonRepository.searchMovieById(movieId);
    }

}
