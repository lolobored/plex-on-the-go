package org.lolobored.plex.spring.controllers;


import org.lolobored.plex.elasticsearch.search.SearchRequest;
import org.lolobored.plex.elasticsearch.search.SearchResponse;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.MoviesService;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping({"/plex-backend/movies"})
public class MoviesController {


	@Autowired
	MoviesService moviesService;
	@Autowired
	UserService userService;

	@PostMapping(path = {"/search"})
	@PreAuthorize("isAuthenticated()")
	public SearchResponse searchMovies(@RequestBody SearchRequest searchRequest, Principal principal) {
		User user = userService.getUserById(principal.getName());
		return moviesService.searchMovies(user.getUserName(), searchRequest);
	}

	@GetMapping(path = {"/genre"})
	@PreAuthorize("isAuthenticated()")
	public List<String> getGenre() {
		return moviesService.getGenres();
	}

	@GetMapping(path = {"/year"})
	@PreAuthorize("isAuthenticated()")
	public List<String> getYears() {
		return moviesService.getYears();
	}
}
