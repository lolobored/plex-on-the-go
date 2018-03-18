package org.lolobored.plex.spring.controllers;

import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping({"/users"})
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUsers()  {
        return userService.getUsers();
    }

    @GetMapping(path ={"/{id}"})
    public User getUser(@PathVariable("id") int id)  {
        return userService.getUser(id);
    }

    @DeleteMapping(path ={"/{id}"})
    public User delete(@PathVariable("id") int id) {
        return userService.deleteUser(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping(path ={"/{id}"})
    public User updateUser(@PathVariable("id") int id, @RequestBody User user){
        return userService.updateUser(id, user);
    }
}
