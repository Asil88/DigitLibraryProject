package com.digitlibraryproject.domain;

import lombok.Data;


import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "role")
    private String role;

    public Role() {
    }

    public Role(int id, int userId, String role) {
        this.id = id;
        this.userId = userId;
        this.role = role;
    }
}
