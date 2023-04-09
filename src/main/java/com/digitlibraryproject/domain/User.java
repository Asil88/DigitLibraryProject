package com.digitlibraryproject.domain;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private int id;

    @NotBlank(message = "Login is mandatory")
    @Size(min = 5, max = 30, message = "Login must be between 6 and 30 characters")
    @Column(name = "login")
    private String login;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be between 8")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{7}", message = "Invalid phone number format")
    @Column(name = "phone_number")
    private String phoneNumber;

    public User() {
    }

    public User(int id, String login, String password, String name, String email, String phoneNumber) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

