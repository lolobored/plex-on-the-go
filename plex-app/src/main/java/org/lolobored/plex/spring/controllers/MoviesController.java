package org.lolobored.plex.spring.controllers;


import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
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

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Media> getMovies(Principal principal) {
		User user = userService.getUserById(principal.getName());
		return moviesService.getMovies(user.getUserName());

	}

	@PostMapping(path = {"/search"})
	@PreAuthorize("isAuthenticated()")
	public List<Media> searchMovies(@RequestBody Search search, Principal principal) {
		User user = userService.getUserById(principal.getName());
		return moviesService.searchMovies(user.getUserName(), search);
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
