package org.lolobored.plex.spring.controllers;


import org.lolobored.plex.model.Media;
import org.lolobored.plex.spring.models.Search;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.TvShowsService;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping({"/plex-backend/tvshows"})
public class TvShowsController {


	@Autowired
	TvShowsService tvShowsService;
	@Autowired
	UserService userService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Media> getTvShows(Principal principal) {
		User user = userService.getUserById(principal.getName());
		return tvShowsService.getTvShows(user.getUserName());

	}

	@PostMapping(path = {"/search"})
	@PreAuthorize("isAuthenticated()")
	public List<Media> searchTvShows(@RequestBody Search search, Principal principal) {
		User user = userService.getUserById(principal.getName());
		return tvShowsService.searchTvShows(user.getUserName(), search);
	}
}
