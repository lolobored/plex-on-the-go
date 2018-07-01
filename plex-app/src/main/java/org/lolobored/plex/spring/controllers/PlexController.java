package org.lolobored.plex.spring.controllers;

import org.lolobored.http.HttpException;
import org.lolobored.plex.exception.PlexException;
import org.lolobored.plex.spring.models.PlexUser;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.PlexServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping({"/plex-backend/plex"})
public class PlexController {

	@Autowired
	PlexServiceRest plexServiceRest;

	@PostMapping(path = {"/login"})
	@PreAuthorize("isAuthenticated()")
	public boolean login(@RequestBody PlexUser user,
											 @RequestHeader(value = "Authorization") String bearerToken) {
		return plexServiceRest.plexLogin(user.getUsername(), user.getPassword());

	}

	@PostMapping(path = {"/fetch"})
	@PreAuthorize("isAuthenticated()")
	public List<PlexUser> getPlexUserDetails(@RequestBody PlexUser plexUser) throws HttpException, IOException, PlexException, JAXBException {
		return plexServiceRest.getPlexUserDetails(plexUser);
	}

	@PostMapping(path = {"/save"})
	@PreAuthorize("isAuthenticated()")
	public void saveUsers(@RequestBody PlexUser plexUser) throws HttpException, IOException, PlexException, JAXBException {
		plexServiceRest.savePlexUsersDetails(plexUser);
	}

	@DeleteMapping(path = {"/{id}"})
	@PreAuthorize("isAuthenticated()")
	public void delete(@PathVariable("id") String id)  {
		plexServiceRest.deletePlexUser(id);
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<PlexUser> getUsers()  {
		return plexServiceRest.getUsers();
	}
}
