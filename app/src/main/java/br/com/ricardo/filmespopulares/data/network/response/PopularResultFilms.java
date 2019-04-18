package br.com.ricardo.filmespopulares.data.network.response;

import java.util.List;

public class PopularResultFilms {

    private List<PopularResponseFilm> results;

    public PopularResultFilms(List<PopularResponseFilm> results) {
        this.results = results;
    }

    public List<PopularResponseFilm> getResults() {
        return results;
    }

    public void setResults(List<PopularResponseFilm> results) {
        this.results = results;
    }
}
