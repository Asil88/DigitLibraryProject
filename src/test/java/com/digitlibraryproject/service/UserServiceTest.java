package com.digitlibraryproject.service;


import com.digitlibraryproject.domain.User;
import com.digitlibraryproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private User user;

    private List<User> userList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
        user = new User(1, "user1", "password1", "User One", "user1@example.com", "1234567");
        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    public void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(userList);
        Optional<List<User>> optionalUsers = userService.findAllUsers();

        assertTrue(optionalUsers.isPresent());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> optionalUser = userService.getUserById(user.getId());

        assertTrue(optionalUser.isPresent());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testCreateUser() {
        userService.createUser(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(user.getPassword())).thenReturn("newPassword");

        userService.updateUserById(user);

        verify(userRepository, times(1)).saveAndFlush(user);
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testUpdatePassword() {
        String newPassword = "newPassword";
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        assertTrue(userService.updatePassword(user.getId(), newPassword));
        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    public void testUpdateLogin() {
        String newLogin = "newLogin";
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertTrue(userService.updateLogin(user.getId(), newLogin));
        assertEquals(newLogin, user.getLogin());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    public void testDeleteUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertTrue(userService.deleteUserById(user.getId()));

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}