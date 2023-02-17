package com.forms.lightweight.lightweight.controller;

import com.forms.lightweight.lightweight.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignUpController {

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){



        // check if email already exists

        // hash password

        // insert to the database

        // generate JWT token

        return ResponseEntity.ok("User received");
    }
}
