package org.lolobored.plex.spring.services.impl;

import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexService;
import org.lolobored.plex.spring.batch.ESLoaderTask;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.repository.UserRepository;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PlexService plexService;

	@Autowired
	private ESLoaderTask loaderTask;

	@Override
	public User addUser(User user) {
		String plexToken = null;
		// try to authenticate to Plex
		try {
			plexToken = plexService.authenticate(user.getPlexLogin(), user.getPlexPassword());
			loaderTask.loadElasticSearch();

		} catch (HttpException | IOException e) {
			// authentication failed
		}
		user.setPlexToken(plexToken);
		User newUser= repository.save(user);
		return newUser;
	}

	@Override
	public User deleteUser(Integer id) {
		Optional<User> user = repository.findById(id);
		repository.deleteById(id);
		return user.get();
	}

	@Override
	public List<User> getUsers() {
		return repository.findAll();
	}

	@Override
	public User getUser(Integer id) {
		Optional<User> user = repository.findById(id);
		return user.get();
	}

	@Override
	public User getUser(String username) {
		Optional<User> user = repository.findByUserName(username);
		if (!user.isPresent()){
			User newuser = new User();
			newuser.setUserName(username);
			repository.save(newuser);
			user = repository.findByUserName(username);
		}
		return user.get();
	}

	@Override
	public User updateUser(Integer id, User user) {
		String plexToken = null;
		// try to authenticate to Plex
		try {
			plexToken = plexService.authenticate(user.getPlexLogin(), user.getPlexPassword());
			loaderTask.loadElasticSearch();

		} catch (HttpException | IOException e) {
			// authentication failed
		}
		user.setPlexToken(plexToken);
		return repository.save(user);
	}
}
