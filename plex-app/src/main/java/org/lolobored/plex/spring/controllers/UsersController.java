package org.lolobored.plex.spring.controllers;

import org.lolobored.plex.spring.models.User;
import org.lolobored.plex.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping({"/plex-backend/users"})
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<User> getUsers()  {
        return userService.getUsers();
    }

    @GetMapping(path ={"/{id}"})
    @PreAuthorize("isAuthenticated()")
    public User getUser(@PathVariable("id") int id)  {
        return userService.getUser(id);
    }

    @GetMapping(path ={"/byname/{username}"})
    @PreAuthorize("isAuthenticated()")
    public User getUserByName(@PathVariable("username") String user){
        return userService.getUser(user);
    }

    @DeleteMapping(path ={"/{id}"})
    @PreAuthorize("isAuthenticated()")
    public User delete(@PathVariable("id") int id) {
        return userService.deleteUser(id);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping(path ={"/{id}"})
    @PreAuthorize("isAuthenticated()")
    public User updateUser(@PathVariable("id") int id, @RequestBody User user){
        return userService.updateUser(id, user);
    }
}
