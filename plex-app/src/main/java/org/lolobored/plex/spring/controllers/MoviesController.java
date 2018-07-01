package org.lolobored.plex.spring.controllers;


import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.PlexUser;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping({"/plex-backend/movies"})
public class MoviesController {


	@Autowired
	MovieService movieService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Media> getMovies(Principal principal) {
		return movieService.getMovies(principal.getName());

	}

	@PostMapping(path = {"/search"})
	@PreAuthorize("isAuthenticated()")
	public List<Media> searchMovies(@RequestBody Search search, Principal principal) {

		return movieService.searchMovies(principal.getName(), search);
	}

	@GetMapping(path = {"/genre"})
	@PreAuthorize("isAuthenticated()")
	public List<String> getGenre() {
		return movieService.getGenres();
	}

	@GetMapping(path = {"/year"})
	@PreAuthorize("isAuthenticated()")
	public List<Integer> getYears() {
		return movieService.getYears();
	}
}
