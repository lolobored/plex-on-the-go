package org.lolobored.plex.spring.controllers;


import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/movies"})
public class MoviesController {


    @Autowired
    MovieService movieService;

    @GetMapping
    public List<Media> getMovies()  {
        return movieService.getMovies();
    }

    @PostMapping(path ={"/search"})
    public List<Media> searchMovies(@RequestBody Search search)  {
        return movieService.searchMovies(search);
    }

    @GetMapping(path ={"/genre"})
    public List<String> getGenre()  {
        return movieService.getGenres();
    }

    @GetMapping(path ={"/year"})
    public List<Integer> getYears()  {
        return movieService.getYears();
    }
}
