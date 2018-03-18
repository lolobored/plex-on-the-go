package org.lolobored.plex.spring.services.impl;

import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.repository.UserRepository;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Override
    public User addUser(User user) {
        return repository.save(user);
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
    public User updateUser(Integer id, User user) {
        return repository.save(user);
    }
}
