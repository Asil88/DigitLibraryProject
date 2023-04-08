package com.digitlibraryproject.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "role")
    private String role;


}
