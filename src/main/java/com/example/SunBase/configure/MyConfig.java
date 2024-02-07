package com.example.SunBase.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MyConfig {


   // {"jwtToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHN1bmJhc2VkYXRhLmNvbSIsImlhdCI6MTcwNzI4NjA2NCwiZXhwIjoxNzA3MzA0MDY0fQ.AWeh9BrGfWiMTLVBEyXH7skQcOKspuwdSjErb7_hYsg","username":"test@sunbasedata.com"}

        @Bean
        public UserDetailsService userDetailsService() {
            System.out.println("i am in user twinkle user detail service ");
            UserDetails userDetails = User.builder().
                    username("test@sunbasedata.com")
                    .password(passwordEncoder().encode("Test@123")).roles("ADMIN").
                    build();
            return new InMemoryUserDetailsManager(userDetails);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
            return builder.getAuthenticationManager();
        }

}
