package org.lolobored.plex.spring.controllers;


import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/movies"})
public class MoviesController {


    @Autowired
    MovieService movieService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public List<Media> getMovies(@RequestBody String user)  {

        return movieService.getMovies(user);
    }

    @PostMapping(path ={"/search"})
    @PreAuthorize("isAuthenticated()")
    public List<Media> searchMovies(@RequestBody Search search)  {

        return movieService.searchMovies(search);
    }

    @GetMapping(path ={"/genre"})
    @PreAuthorize("isAuthenticated()")
    public List<String> getGenre()  {
        return movieService.getGenres();
    }

    @GetMapping(path ={"/year"})
    @PreAuthorize("isAuthenticated()")
    public List<Integer> getYears()  {
        return movieService.getYears();
    }
}
