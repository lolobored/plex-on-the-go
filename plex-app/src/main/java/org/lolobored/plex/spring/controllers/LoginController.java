package org.lolobored.plex.spring.controllers;

import org.lolobored.plex.spring.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping(path = "/login", consumes = "application/json")
    public String getLogin(@RequestBody User user){

        return "success";
    }
}
