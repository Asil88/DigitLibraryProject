package com.digitlibraryproject.controller;

import com.digitlibraryproject.domain.User;
import com.digitlibraryproject.exception.ResourceNotFoundException;
import com.digitlibraryproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> allUsers = userService.findAllUsers().orElseThrow(() -> new ResourceNotFoundException("USERS_NOT_FOUND"));
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User userById = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<HttpStatus> updateUserById(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.updateUserById(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<HttpStatus> updateUserPassword(@PathVariable Integer id,@Valid @RequestBody String newPassword) {
        boolean isUpdated = userService.updatePassword(id, newPassword);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateLogin/{id}")
    public ResponseEntity<HttpStatus> updateUserLogin(@PathVariable Integer id,@Valid @RequestBody String newLogin) {
        boolean isUpdated = userService.updateLogin(id, newLogin);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
