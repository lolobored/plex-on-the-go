package org.lolobored.plex.spring.services;

import org.lolobored.plex.spring.models.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    User deleteUser(Integer id);

    List<User> getUsers();

    User getUser(Integer id);

    User updateUser(Integer id, User user);
}
