
package com.dacruzl2.btgdesafio.model.pojos.pojoteste;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import com.dacruzl2.btgdesafio.model.pojos.Genre;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    private List<Integer> mGenreIds;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "original_lang")
    @SerializedName("original_language")
    private String mOriginalLanguage;

    @Ignore
    @SerializedName("original_title")
    private String mOriginalTitle;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String mOverview;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private Double mPopularity;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String mPosterPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String mReleaseDate;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String mTitle;

    @ColumnInfo(name = "video")
    @SerializedName("video")
    private Boolean mVideo;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private float mVoteAverage;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private Long mVoteCount;

    @ColumnInfo(name = "favorite")
    private boolean isFavorite;

    public Movie() {
    }

    public Movie(int movieId, String movieTitle, String movieOverview, String movieReleaseDate,
                 String moviePoster, String backdrop, float voterAverage, boolean isFavourite, List<Integer> mGenreIds) {
        this.mId = movieId;
        this.mTitle = movieTitle;
        this.mOverview = movieOverview;
        this.mReleaseDate = movieReleaseDate;
        this.mPosterPath = moviePoster;
        this.mBackdropPath = backdrop;
        this.mVoteAverage = voterAverage;
        this.isFavorite = isFavourite;
        this.mGenreIds = mGenreIds;
    }

    @Ignore
    public Movie(String movieTitle, String movieOverview, String movieReleaseDate, String moviePoster, String backdrop,
        float voterAverage, boolean isFavourite){
            this.mTitle = movieTitle;
            this.mOverview = movieOverview;
            this.mReleaseDate = movieReleaseDate;
            this.mPosterPath = moviePoster;
            this.mBackdropPath = backdrop;
            this.mVoteAverage = voterAverage;
            this.isFavorite = isFavourite;
        }

        public boolean isFavorite () {
            return isFavorite;
        }

        public void setFavorite ( boolean favorite){
            isFavorite = favorite;
        }

        public String getBackdropPath () {
            return mBackdropPath;
        }

        public void setBackdropPath (String backdropPath){
            mBackdropPath = backdropPath;
        }

        public List<Integer> getGenreIds () {
            return mGenreIds;
        }

        public void setGenreIds (List<Integer> genreIds) {
            mGenreIds = genreIds;
        }

        public int getId () {
            return mId;
        }

        public void setId ( int id){
            mId = id;
        }

        public String getOriginalLanguage () {
            return mOriginalLanguage;
        }

        public void setOriginalLanguage (String originalLanguage){
            mOriginalLanguage = originalLanguage;
        }

        public String getOriginalTitle () {
            return mOriginalTitle;
        }

        public void setOriginalTitle (String originalTitle){
            mOriginalTitle = originalTitle;
        }

        public String getOverview () {
            return mOverview;
        }

        public void setOverview (String overview){
            mOverview = overview;
        }

        public Double getPopularity () {
            return mPopularity;
        }

        public void setPopularity (Double popularity){
            mPopularity = popularity;
        }

        public String getPosterPath () {
            return mPosterPath;
        }

        public void setPosterPath (String posterPath){
            mPosterPath = posterPath;
        }

        public String getReleaseDate () {
            return mReleaseDate;
        }

        public void setReleaseDate (String releaseDate){
            mReleaseDate = releaseDate;
        }

        public String getTitle () {
            return mTitle;
        }

        public void setTitle (String title){
            mTitle = title;
        }

        public Boolean getVideo () {
            return mVideo;
        }

        public void setVideo (Boolean video){
            mVideo = video;
        }

        public float getVoteAverage () {
            return mVoteAverage;
        }

        public void setVoteAverage ( float voteAverage){
            mVoteAverage = voteAverage;
        }

        public Long getVoteCount () {
            return mVoteCount;
        }

        public void setVoteCount (Long voteCount){
            mVoteCount = voteCount;
        }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBackdropPath);
        dest.writeList(this.mGenreIds);
        dest.writeInt(this.mId);
        dest.writeString(this.mOriginalLanguage);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mOverview);
        dest.writeValue(this.mPopularity);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mTitle);
        dest.writeValue(this.mVideo);
        dest.writeFloat(this.mVoteAverage);
        dest.writeValue(this.mVoteCount);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.mBackdropPath = in.readString();
        this.mGenreIds = new ArrayList<Integer>();
        in.readList(this.mGenreIds, Integer.class.getClassLoader());
        this.mId = in.readInt();
        this.mOriginalLanguage = in.readString();
        this.mOriginalTitle = in.readString();
        this.mOverview = in.readString();
        this.mPopularity = (Double) in.readValue(Double.class.getClassLoader());
        this.mPosterPath = in.readString();
        this.mReleaseDate = in.readString();
        this.mTitle = in.readString();
        this.mVideo = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mVoteAverage = in.readFloat();
        this.mVoteCount = (Long) in.readValue(Long.class.getClassLoader());
        this.isFavorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}