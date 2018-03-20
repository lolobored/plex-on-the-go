package org.lolobored.plex.spring.services;

import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.models.User;

import java.util.List;

public interface MovieService {

    List<String> getGenres();
    List<Integer> getYears();
    List<Media> getMovies(String user);
    List<Media> searchMovies(Search search);
}
