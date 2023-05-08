package com.armaninvestment.parsparandreporter.controllers;

import com.armaninvestment.parsparandreporter.entities.User;
import com.armaninvestment.parsparandreporter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private final UserRepository repository;
    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }
    @GetMapping(path = {"/",""})
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(repository.findAll());
    }
}
