package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import com.appsdeveloperblog.ws.api.ResourceServer.response.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port: " + env.getProperty("local.server.port");
    }

    @PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject")
    //@Secured("ROLE_developer")
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable("id") String id, @AuthenticationPrincipal Jwt jwt) {
        return "Deleted user with id " + id + " and JWT subject " + jwt.getSubject();
    }

    @PostAuthorize("returnObject.userId == #jwt.subject")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable("id") String id, @AuthenticationPrincipal Jwt jwt) {
        return new UserRest("Kim Long", "Ngo", "01ff9fe9-5b4d-4cf5-8132-c39c4d5edb0b");
    }
}
