package com.digitlibraryproject.domain.request;

import lombok.Data;


@Data
public class RegistrationUser {
    private String login;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
}
