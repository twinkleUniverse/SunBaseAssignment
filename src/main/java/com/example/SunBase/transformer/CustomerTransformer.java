package com.example.SunBase.transformer;

import com.example.SunBase.dtos.CustomerRequestDto;
import com.example.SunBase.dtos.CustomerResponseDto;
import com.example.SunBase.model.Customer;

public class CustomerTransformer {
    public static CustomerResponseDto CustomerToResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhoneNo())
                .email(customer.getEmail())
                .state(customer.getState())
                .city(customer.getCity())
                .address(customer.getAddress())
                .build();
    }


    public static Customer CustomerRDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .address(customerRequestDto.getAddress())
                .state(customerRequestDto.getState())
                .city(customerRequestDto.getCity())
                .email(customerRequestDto.getEmail())
                .phoneNo(customerRequestDto.getPhone())
                .build();
    }
}
