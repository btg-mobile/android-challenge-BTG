package br.com.ricardo.filmespopulares.data.network.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {

    private int idMovie;
    private String rate;
    private String title;
    private String posterPath;
    private String originalTitle;
    private ArrayList<Integer> genres;
    private String backdropPath;
    private String overview;
    private String releaseDate;

    public Film(int idMovie, String rate, String title, String posterPath, String originalTitle, ArrayList<Integer> genres, String backdropPath, String overview, String releaseDate) {
        this.idMovie = idMovie;
        this.rate = rate;
        this.title = title;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.genres = genres;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
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
