package org.lolobored.plex.spring.services.impl;

import org.lolobored.plex.elasticsearch.ElasticSearchService;
import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {


    @Autowired
    private ElasticSearchService elasticSearch;

    @Override
    public List<String> getGenres() {
        return elasticSearch.getMoviesGenre();
    }

    @Override
    public List<Integer> getYears() {
        return elasticSearch.getAllYears();
    }

    @Override
    public List<Media> getMovies(String user) {

        return elasticSearch.getAllMovies(user);
    }

    @Override
    public List<Media> searchMovies(Search search) {
        String user = search.getUser();
        return elasticSearch.searchMovies(user, search.getGenres(), search.getYearFrom(), search.getYearTo());
    }
}
