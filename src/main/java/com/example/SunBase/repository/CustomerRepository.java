package com.example.SunBase.repository;

import com.example.SunBase.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    /*Optional<Customer> findByFirstName(String name);*/
   Customer findByPhoneNo(String phone);
   Customer findByEmail(String email);

   List<Customer> findAllByFirstName(String firstName);
    List<Customer> findAllByLastName(String lastName);
    List<Customer> findAllByCity(String city);
    List<Customer> findAllByState(String state);



}
