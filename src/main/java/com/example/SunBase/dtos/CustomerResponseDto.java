package com.example.SunBase.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerResponseDto {
    String firstName;
    String lastName;
    String address;
    String city;
    String state;
    String email;
    String phone;

}
