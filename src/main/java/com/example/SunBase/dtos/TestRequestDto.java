package com.example.SunBase.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
@Builder
public class TestRequestDto {
    String login_id;
    String password;
}
