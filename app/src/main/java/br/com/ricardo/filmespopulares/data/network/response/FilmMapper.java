package br.com.ricardo.filmespopulares.data.network.response;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardo.filmespopulares.data.network.model.Film;

public class FilmMapper {

    public static List<Film> setFilmDomain(List<ResponseFilm> responseFilms){

        List<Film> filmList = new ArrayList<>();

        for(ResponseFilm rf : responseFilms){
            final Film film = new Film(rf.getId(), rf.getRate(), rf.getTitle(), rf.getPosterPath(), rf.getOriginalTitle(),
                    rf.getGenres(), rf.getBackdropPath(), rf.getOverview(), rf.getReleaseDate());

            filmList.add(film);
        }

        return filmList;
    }
}
