package com.example.android_challenge_btg.models.responses;

import com.example.android_challenge_btg.models.Movie;

import java.math.BigInteger;
import java.util.List;

public class MoviesResponse {
    private int page;
    private BigInteger totalResults;
    private BigInteger totalPages;
    private List<Movie> results;

    public MoviesResponse(int page, BigInteger totalResults, BigInteger totalPages, List<Movie> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public BigInteger getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(BigInteger totalResults) {
        this.totalResults = totalResults;
    }

    public BigInteger getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(BigInteger totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
