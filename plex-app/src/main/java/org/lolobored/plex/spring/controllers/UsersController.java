package org.lolobored.plex.spring.controllers;

import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/plex-backend/users"})
public class UsersController {

	private static String DUMMY_PASSWORD = "dummy";

	@Autowired
	UserService userService;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<User> getUsers() {
		List<User> users = userService.getUsers();
		for (User user : users) {
			// ensure we do not send back the passwords
			user.setPassword(DUMMY_PASSWORD);
			user.setPlexPassword(DUMMY_PASSWORD);
		}
		return users;
	}

	@GetMapping(path = {"/{id}"})
	@PreAuthorize("isAuthenticated()")
	public User getUser(@PathVariable("id") String id) {
		User user = userService.getUserById(id);
		// ensure we do not send back the passwords
		user.setPassword(DUMMY_PASSWORD);
		user.setPlexPassword(DUMMY_PASSWORD);
		return user;
	}

	@GetMapping(path = {"/byname/{username}"})
	@PreAuthorize("isAuthenticated()")
	public User getUserByName(@PathVariable("username") String username) {

		User user = userService.getUser(username);
		// ensure we do not send back the passwords
		user.setPassword(DUMMY_PASSWORD);
		user.setPlexPassword(DUMMY_PASSWORD);
		return user;

	}

	@DeleteMapping(path = {"/{id}"})
	@PreAuthorize("isAuthenticated()")
	public void delete(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(bearerToken.indexOf(" ")).trim();
		userService.deleteUser(bearerToken, id);
	}

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public User addUser(@RequestBody User user, @RequestHeader(value = "Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(bearerToken.indexOf(" ")).trim();
		user = userService.addUser(bearerToken, user);
		// ensure we do not send back the passwords
		user.setPassword(DUMMY_PASSWORD);
		user.setPlexPassword(DUMMY_PASSWORD);
		return user;
	}

	@PutMapping(path = {"/{id}"})
	@PreAuthorize("isAuthenticated()")
	public User updateUser(@PathVariable("id") int id, @RequestBody User user, @RequestHeader(value = "Authorization") String bearerToken) {
		bearerToken = bearerToken.substring(bearerToken.indexOf(" ")).trim();
		user = userService.updateUser(bearerToken, user);
		// ensure we do not send back the passwords
		user.setPassword(DUMMY_PASSWORD);
		user.setPlexPassword(DUMMY_PASSWORD);
		return user;
	}
}
