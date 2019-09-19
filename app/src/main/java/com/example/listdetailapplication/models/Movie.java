package com.example.listdetailapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "imdb_id")
    private String imdbID;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "imdb")
    private String imdb;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "timestamp")
    private int timestamp;


    // DETAIL PAGE

    @ColumnInfo(name = "meta_score")
    private int metaScore;

    @ColumnInfo(name = "imdb_rating")
    private int imdbRating;

    @ColumnInfo(name = "imdb_votes")
    private int imdbVotes;

    @ColumnInfo(name = "dvd")
    private int dvd;

    @ColumnInfo(name = "box_office")
    private int boxOffice;

    @ColumnInfo(name = "production")
    private int production;

    @ColumnInfo(name = "website")
    private int website;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(@NonNull String imdbID) {
        this.imdbID = imdbID;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(int metaScore) {
        this.metaScore = metaScore;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(int imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(int imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public int getDvd() {
        return dvd;
    }

    public void setDvd(int dvd) {
        this.dvd = dvd;
    }

    public int getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(int boxOffice) {
        this.boxOffice = boxOffice;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getWebsite() {
        return website;
    }

    public void setWebsite(int website) {
        this.website = website;
    }

    public Movie()
    {

    }

    public Movie(@NonNull String imdbId, String title, String year, String imdb, String type, String poster, int timestamp) {
        this.imdbID = imdbId;
        this.title = title;
        this.year = year;
        this.imdb = imdb;
        this.type = type;
        this.poster = poster;
        this.timestamp = timestamp;
    }
}















