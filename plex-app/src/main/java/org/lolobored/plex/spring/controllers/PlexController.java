package org.lolobored.plex.spring.controllers;

import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.PlexServiceRest;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/plex-backend/plex"})
public class PlexController {

	@Autowired
	PlexServiceRest plexServiceRest;

	@PostMapping(path = {"/login"})
	@PreAuthorize("isAuthenticated()")
	public boolean login(@RequestBody User user,
											 @RequestHeader(value = "Authorization") String bearerToken) {
		return plexServiceRest.plexLogin(user.getPlexLogin(), user.getPlexPassword());

	}

}
