package com.example.SunBase.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
@Builder
public class CustomerRequestDto {
    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    String email;
    String phone;
}
