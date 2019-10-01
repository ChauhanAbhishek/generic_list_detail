package com.example.listdetailapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "movies")
public class Movie implements Serializable  {

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

    @ColumnInfo(name = "bookmarked")
    private int bookmarked;

    @ColumnInfo(name = "fresh_data")
    private int freshData;


    // DETAIL PAGE

    @ColumnInfo(name = "meta_score")
    private String metaScore;

    @ColumnInfo(name = "imdb_rating")
    private String imdbRating;

    @ColumnInfo(name = "imdb_votes")
    private String imdbVotes;

    @ColumnInfo(name = "dvd")
    private String dvd;

    @ColumnInfo(name = "box_office")
    private String boxOffice;

    @ColumnInfo(name = "production")
    private String production;

    @ColumnInfo(name = "website")
    private String website;



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

    public int getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(int bookmarked) {
        this.bookmarked = bookmarked;
    }

    public int getFreshData() {
        return freshData;
    }

    public void setFreshData(int freshData) {
        this.freshData = freshData;
    }

    @NonNull
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(@NonNull String imdbID) {
        this.imdbID = imdbID;
    }

    public String getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(String metaScore) {
        this.metaScore = metaScore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getDvd() {
        return dvd;
    }

    public void setDvd(String dvd) {
        this.dvd = dvd;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return timestamp == movie.timestamp &&
                bookmarked == movie.bookmarked &&
                freshData == movie.freshData &&
                imdbID.equals(movie.imdbID) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(year, movie.year) &&
                Objects.equals(imdb, movie.imdb) &&
                Objects.equals(type, movie.type) &&
                Objects.equals(poster, movie.poster) &&
                Objects.equals(metaScore, movie.metaScore) &&
                Objects.equals(imdbRating, movie.imdbRating) &&
                Objects.equals(imdbVotes, movie.imdbVotes) &&
                Objects.equals(dvd, movie.dvd) &&
                Objects.equals(boxOffice, movie.boxOffice) &&
                Objects.equals(production, movie.production) &&
                Objects.equals(website, movie.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbID, title, year, imdb, type, poster, timestamp, bookmarked, freshData, metaScore, imdbRating, imdbVotes, dvd, boxOffice, production, website);
    }
}















