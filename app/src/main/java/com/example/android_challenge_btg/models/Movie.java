package com.example.android_challenge_btg.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@DatabaseTable
public class Movie {
    @DatabaseField
    private BigDecimal popularity;
    @DatabaseField
    private BigInteger voteCount;
    @DatabaseField
    private boolean video;
    @DatabaseField
    private String posterPath;
    @DatabaseField(id = true, canBeNull = false)
    private long id;
    @DatabaseField
    private boolean adult;
    @DatabaseField
    private String backdropPath;
    @DatabaseField
    private String originalLanguage;
    @DatabaseField
    private String originalTitle;
    @DatabaseField
    private String genre_ids;
    @DatabaseField
    private String title;
    @DatabaseField
    private BigDecimal voteAverage;
    @DatabaseField
    private String overview;
    @DatabaseField
    private String releaseDate;

    public Movie(BigDecimal popularity, BigInteger voteCount, boolean video, String posterPath,
                 long id, boolean adult, String backdropPath, String originalLanguage,
                 String originalTitle, String genre_ids, String title, BigDecimal voteAverage,
                 String overview, String releaseDate) {
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.posterPath = posterPath;
        this.id = id;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genre_ids = genre_ids;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public BigInteger getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(BigInteger voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_id(String genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(BigDecimal voteAverage) {
        this.voteAverage = voteAverage;
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