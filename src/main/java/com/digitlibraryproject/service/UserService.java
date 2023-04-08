package com.digitlibraryproject.service;

import com.digitlibraryproject.domain.User;
import com.digitlibraryproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<List<User>> findAllUsers() {
        return Optional.of((ArrayList<User>) userRepository.findAll());
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUserById(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public boolean updatePassword(Integer id, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.saveAndFlush(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateLogin(Integer id, String newLogin) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLogin(newLogin);
            userRepository.saveAndFlush(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUserById(Integer id) {
        Optional<User> optionalOrder = userRepository.findById(id);
        try {
            if (optionalOrder.isPresent()) {
                userRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
