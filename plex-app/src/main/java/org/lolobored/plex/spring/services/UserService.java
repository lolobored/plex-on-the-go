package org.lolobored.plex.spring.services;

import org.lolobored.plex.spring.models.User;

import java.util.List;

public interface UserService {

    User addUser(String authToken, User user);

    void deleteUser(String authToken, String id);

    List<User> getUsers();

    User getUserById(String id);

    User getUser(String username);

    User updateUser(String authToken, User user);
}
