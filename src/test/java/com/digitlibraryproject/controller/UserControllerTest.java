package com.digitlibraryproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.digitlibraryproject.domain.User;
import com.digitlibraryproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        user = new User(1, "user1", "password1", "User One", "user1@example.com", "1234567");
        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testFindAllUsers() throws Exception {
        when(userService.findAllUsers()).thenReturn(Optional.of(userList));

        mockMvc.perform(get("/user/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(user.getId())))
                .andExpect(jsonPath("$[0].login", is(user.getLogin())))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$[0].phoneNumber", is(user.getPhoneNumber())));
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testGetUserById() throws Exception {
        when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.login", is(user.getLogin())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testUpdateUserById() throws Exception {
        doNothing().when(userService).updateUserById(user);

        mockMvc.perform(put("/user/updateInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testUpdateUserPassword() throws Exception {
        int id = 1;
        String newPassword = "newpassword";
        when(userService.updatePassword(id, newPassword)).thenReturn(true);

        mockMvc.perform(put("/user/updatePassword/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testUpdateUserLogin() throws Exception {
        int id = 1;
        String newLogin = "newlogin";
        when(userService.updateLogin(id, newLogin)).thenReturn(true);

        mockMvc.perform(put("/user/updateLogin/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newLogin))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "login", password = "password")
    void testDeleteUserById() throws Exception {
        int id = 1;
        when(userService.deleteUserById(id)).thenReturn(true);

        mockMvc.perform(delete("/user/{id}", id))
                .andExpect(status().isNoContent());
    }
}
