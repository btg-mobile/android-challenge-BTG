package com.example.android_challenge_btg.models;

import com.example.android_challenge_btg.services.DataBaseService;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
    private String genreIdsStr;
    @DatabaseField
    private String title;
    @DatabaseField
    private BigDecimal voteAverage;
    @DatabaseField
    private String overview;
    @DatabaseField
    private String releaseDate;

    private List<Long> genreIds;
    private final String LIST_SEPARATOR = "-";


    public Movie(BigDecimal popularity, BigInteger voteCount, boolean video, String posterPath,
                 long id, boolean adult, String backdropPath, String originalLanguage,
                 String originalTitle, String genreIdsStr, String title, BigDecimal voteAverage,
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
        this.genreIdsStr = genreIdsStr;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie(Movie movie) {
        this.popularity = movie.getPopularity();
        this.voteCount = movie.getVoteCount();
        this.video = movie.isVideo();
        this.posterPath = movie.getPosterPath();
        this.id = movie.getId();
        this.adult = movie.isAdult();
        this.backdropPath = movie.getBackdropPath();
        this.originalLanguage = movie.getOriginalLanguage();
        this.originalTitle = movie.getOriginalTitle();
        this.genreIdsStr = movie.getGenreIdsStr();
        this.title = movie.getTitle();
        this.voteAverage = movie.getVoteAverage();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
    }

    public Movie() {
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

    public String getGenreIdsStr() {
        if(this.genreIdsStr == null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Long genreId : this.genreIds) {
                stringBuilder.append(genreId).append(LIST_SEPARATOR);
            }
            stringBuilder.setLength(stringBuilder.length() - LIST_SEPARATOR.length());
            this.genreIdsStr = stringBuilder.toString();
        }
        return genreIdsStr;
    }

    public void setGenreIdsStr(String genreIdsStr) {
        this.genreIdsStr = genreIdsStr;
    }

    public List<Long> getGenreIds() {
        if(this.genreIds == null) {
            this.genreIds = new ArrayList<>();
            String[] genreIdsSplitted = this.genreIdsStr.split(LIST_SEPARATOR);
            for (String genreId: genreIdsSplitted) {
                this.genreIds.add(Long.valueOf(genreId));
            }
        }
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Genre> getGenres(DataBaseService dataBaseService) {
        RuntimeExceptionDao<Genre, Long> genreDao = dataBaseService.getRuntimeExceptionDao(Genre.class);
        List<Genre> genres = new ArrayList<>();
        for (Long genreId : this.getGenreIds()) {
            genres.add(genreDao.queryForId(genreId));
        }

        return genres;
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