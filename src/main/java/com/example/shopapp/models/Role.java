package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 20)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> userSet;

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
