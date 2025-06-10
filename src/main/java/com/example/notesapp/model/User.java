package com.example.notesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Username must not be empty")
    @Column(name = "user_name")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    @Column(name = "password")
    private String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

}
