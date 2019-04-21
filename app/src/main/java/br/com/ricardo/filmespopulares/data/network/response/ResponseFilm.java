package br.com.ricardo.filmespopulares.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseFilm implements Serializable {

    private int id;
    @SerializedName("vote_average")
    private String rate;
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genres;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;


    public ResponseFilm(int id, String rate, String title, String posterPath, String originalTitle,
                        ArrayList<Integer> genres, String backdropPath, String overview, String releaseDate) {
        this.id = id;
        this.rate = rate;
        this.title = title;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.genres = genres;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
