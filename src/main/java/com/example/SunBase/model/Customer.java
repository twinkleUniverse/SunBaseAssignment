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
@Table(name ="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="first_name")
    String firstName;
    @Column(name="last_name")
    String lastName;
    @Column(name="email",nullable = false,unique = true)
    String email;
    @Column(name="phoneNo",nullable = false,unique = true)
    String phoneNo;
    @Column(name="street")
    String street;
    @Column(name="address")
    String address;
    @Column(name="city")
    String city;
    @Column(name="state")
    String state;


}
