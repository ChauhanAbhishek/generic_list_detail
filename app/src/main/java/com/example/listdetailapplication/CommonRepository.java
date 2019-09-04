package com.example.listdetailapplication;

import android.util.Log;

import com.example.listdetailapplication.database.NoteDao;

public class CommonRepository {

    public  CommonRepository(ApiService apiService, NoteDao noteDao)
    {
        Log.d("check not", apiService + " n");
        Log.d("check not", noteDao + " n");


    }
}
