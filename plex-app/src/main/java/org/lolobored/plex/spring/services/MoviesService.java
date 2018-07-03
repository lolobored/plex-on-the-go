package org.lolobored.plex.spring.services;

import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;

import java.util.List;

public interface MoviesService {

    List<String> getGenres();
    List<String> getYears();
    List<Media> getMovies(String user);
    List<Media> searchMovies(String username, Search search);

}
