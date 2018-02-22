package com.moviemania.akash.projectwork;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Akash on 13-Aug-16.
 */
public class Movie implements Parcelable {

    private long id;
    private String title;
    private Date releaseDateTheater;
    private int audienceScore;
    private String overview;
    private String urlThumnail;
    private String urlThumnail1;
    private String key;


    public Movie() {

    }

    public Movie(Parcel input) {

        id = input.readLong();
        title = input.readString();
        releaseDateTheater = new Date(input.readLong());
        audienceScore = input.readInt();
        overview=input.readString();
        urlThumnail=input.readString();
        urlThumnail1=input.readString();
        key=input.readString();

    }


    public Movie(long id, String title, Date releaseDateTheater, int audienceScore, String overview, String urlThumnail,String urlThumnail1,String key)

    {
        this.id = id;
        this.title = title;
        this.releaseDateTheater = releaseDateTheater;
        this.audienceScore = audienceScore;
        this.overview = overview;
        this.urlThumnail = urlThumnail;
        this.urlThumnail1 = urlThumnail1;
        this.key=key;


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(Date releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUrlThumnail() {
        return urlThumnail;
    }

    public void setUrlThumnail(String urlThumnail) {
        this.urlThumnail = urlThumnail;
    }

    public String getUrlThumnail1() {
        return urlThumnail1;
    }

    public void setUrlThumnail1(String urlThumnail1) {
        this.urlThumnail1 = urlThumnail1;
    }

    public String getkey() {
        return key;
    }

    public void setkey(String urlThumnail1) {
        this.key = key;
    }


    public String toString() {
        return "ID: " + id + "Title " + title + "Date " + releaseDateTheater + "Overview " + overview + "Score " + audienceScore + "urlThumbnail " + urlThumnail+ "urlThumbnail1 " + urlThumnail1+"key"+key;


    }

    @Override
    public int describeContents() {
        L.m("describe content movie");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        L.m("write to parcel movie");
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(releaseDateTheater.getTime());
        dest.writeInt(audienceScore);
        dest.writeString(overview);
        dest.writeString(urlThumnail);
        dest.writeString(urlThumnail1);
        dest.writeString(key);
    }


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {


        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };


}