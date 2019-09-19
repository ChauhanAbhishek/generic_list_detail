package com.example.listdetailapplication.list;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.listdetailapplication.CommonApplication;
import com.example.listdetailapplication.CommonRepository;

import javax.inject.Inject;

public class ListViewModel extends ViewModel {

    CommonRepository mCommonRepository;
    public static final String QUERY_EXHAUSTED = "No more results.";
    @Inject
    public ListViewModel(CommonRepository repository)
    {
        Log.d("check not null " , repository + " ls");
        mCommonRepository = repository;
    }

    public void checkReq()
    {
        mCommonRepository.checkReq();
    }


}
