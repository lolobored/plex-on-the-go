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
    public User getUser(@PathVariable("id") String id)  {
        return userService.getUserById(id);
    }

    @GetMapping(path ={"/byname/{username}"})
    @PreAuthorize("isAuthenticated()")
    public User getUserByName(@PathVariable("username") String user){
        return userService.getUser(user);
    }

    @DeleteMapping(path ={"/{id}"})
    @PreAuthorize("isAuthenticated()")
    public User delete(@PathVariable("id") String id, @RequestHeader(value="Authorization") String bearerToken) {

        return userService.deleteUser(bearerToken, id);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public User addUser(@RequestBody User user, @RequestHeader(value="Authorization") String bearerToken){

        return userService.addUser(bearerToken, user);
    }

    @PutMapping(path ={"/{id}"})
    @PreAuthorize("isAuthenticated()")
    public User updateUser(@PathVariable("id") int id, @RequestBody User user, @RequestHeader(value="Authorization") String bearerToken){
        return userService.updateUser(bearerToken, user);
    }
}
