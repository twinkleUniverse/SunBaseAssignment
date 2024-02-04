package com.example.SunBase.repository;

import com.example.SunBase.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByFirstName(String name);
   Optional<Customer> findByPhoneNo(String phone);
   Optional<Customer> findByEmail(String email);

   List<Customer> findAllByFirstName(String name);
    List<Customer> findAllByLastName(String name);
    List<Customer> findAllByCity(String name);
    List<Customer> findAllByState(String state);



}
