package com.example.SunBase.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name ="user")
public class User {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int userId;
    @Column(name = "login_id",nullable = false,unique = true)
    String username;
    @Column(name="password",nullable = false,unique = true)
    String password;
    @Column(name="role",nullable = false)
    String roles;
}
