package org.lolobored.plex.spring.services.impl;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.lolobored.http.HttpException;
import org.lolobored.plex.PlexService;
import org.lolobored.plex.spring.config.KeycloakConfig;
import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.repository.UserRepository;
import org.lolobored.plex.spring.services.UserService;
import org.lolobored.plex.spring.tasks.ESLoaderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
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

	@Autowired
	private KeycloakConfig keycloakConfig;

	@Override
	public User addUser(String authToken, User user) {
		// create user in keycloak
		String id = createUserInKeycloak(authToken, user);
		user.setId(id);
		// update user in keycloak
		updateUserInKeycloak(authToken, user);
		User newUser = repository.save(user);
		return newUser;
	}

	@Override
	public void deleteUser(String authToken, String id) {
		// update user in keycloak
		deleteUserInKeycloak(authToken, id);
		repository.deleteById(id);
	}

	@Override
	public List<User> getUsers() {
		return repository.findAll();
	}

	@Override
	public User getUserById(String id) {
		Optional<User> user = repository.findById(id);
		return user.get();
	}

	@Override
	public User getUser(String username) {
		Optional<User> user = repository.findByUserName(username);
		return user.get();
	}

	@Override
	public User updateUser(String authToken, User user) {
		// update user in keycloak
		updateUserInKeycloak(authToken, user);
		User newUser = repository.save(user);
		return newUser;
	}

	private String createUserInKeycloak(String authToken, User user) {
		Keycloak kc = Keycloak.getInstance(
				keycloakConfig.getAuthServerUrl(),
				keycloakConfig.getRealm(), // the realm to log in to
				keycloakConfig.getResource(),
				authToken);
		// Get realm
		RealmResource realmResource = kc.realm(keycloakConfig.getRealm());
		UsersResource usersResource = realmResource.users();

		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(user.getPassword());

		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setUsername(user.getUserName());
		Response response = usersResource.create(userRepresentation);
		return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
	}

	private void updateUserInKeycloak(String authToken, User user) {
		CredentialRepresentation credential= null;
		Keycloak kc = Keycloak.getInstance(
				keycloakConfig.getAuthServerUrl(),
				keycloakConfig.getRealm(), // the realm to log in to
				keycloakConfig.getResource(),
				authToken);
		// Get realm
		RealmResource realmResource = kc.realm(keycloakConfig.getRealm());
		UsersResource usersResource = realmResource.users();

		// retrieve user in keycloak
		UserRepresentation userRepresentation = usersResource.get(user.getId()).toRepresentation();
		userRepresentation.setFirstName(user.getFirstName());
		userRepresentation.setLastName(user.getLastName());
		userRepresentation.setEnabled(true);

		if (!"".equals(user.getPassword())) {
			credential = new CredentialRepresentation();
			credential.setType(CredentialRepresentation.PASSWORD);
			credential.setValue(user.getPassword());
			credential.setTemporary(false);
			userRepresentation.setCredentials(Arrays.asList(credential));
		}

		// get the user resource
		UserResource userResource = usersResource.get(user.getId());
		userResource.update(userRepresentation);
		if (credential!=null) {
			userResource.resetPassword(credential);
		}

		// Get realm role "tester" (requires view-realm role)
		RoleRepresentation adminRole = kc.realm("plex").roles()
				.get("admin").toRepresentation();

		if (user.isAdmin()) {
			usersResource.get(user.getId()).roles().realmLevel()
					.add(Arrays.asList(adminRole));
		} else {
			usersResource.get(user.getId()).roles().realmLevel()
					.remove(Arrays.asList(adminRole));
		}
	}

	private void deleteUserInKeycloak(String authToken, String id) {
		Keycloak kc = Keycloak.getInstance(
				keycloakConfig.getAuthServerUrl(),
				keycloakConfig.getRealm(), // the realm to log in to
				keycloakConfig.getResource(),
				authToken);
		// Get realm
		RealmResource realmResource = kc.realm(keycloakConfig.getRealm());
		UsersResource usersResource = realmResource.users();

		// get the user resource
		usersResource.delete(id);
	}
}
