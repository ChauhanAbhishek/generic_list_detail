package com.example.listdetailapplication.list;

import com.example.listdetailapplication.ListActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ListAdapter {

    private ListActivity activity;
    private Picasso picasso;

    @Inject
    public ListAdapter(ListActivity activity,Picasso picasso) {
        this.activity = activity;
        this.picasso = picasso;
    }
}
