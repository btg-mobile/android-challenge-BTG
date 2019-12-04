package com.example.android_challenge_btg.models;

import java.math.BigInteger;
import java.util.List;

public class HttpResult {
    private BigInteger page;
    private BigInteger totalPages;
    private List<Movie> results;

    public HttpResult(BigInteger page, BigInteger totalPages, List<Movie> results) {
        this.page = page;
        this.totalPages = totalPages;
        this.results = results;
    }

    public BigInteger getPage() {
        return page;
    }

    public void setPage(BigInteger page) {
        this.page = page;
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
