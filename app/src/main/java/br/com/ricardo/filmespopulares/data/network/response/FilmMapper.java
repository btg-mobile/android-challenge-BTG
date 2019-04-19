package br.com.ricardo.filmespopulares.data.network.response;

import java.util.ArrayList;
import java.util.List;

import br.com.ricardo.filmespopulares.data.network.model.Film;

public class FilmMapper {

    public static List<Film> setFilmDomain(List<PopularResponseFilm> popularResponseFilms){

        List<Film> filmList = new ArrayList<>();

        for(PopularResponseFilm fr : popularResponseFilms){
            final Film film = new Film(fr.getId(), fr.getRate(), fr.getTitle(), fr.getPosterPath(), fr.getOriginalTitle(),
                    fr.getGenres(), fr.getBackdropPath(), fr.getOverview(), fr.getReleaseDate());
            filmList.add(film);
        }

        return filmList;
    }
}
