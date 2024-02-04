package com.example.SunBase.service.serviceimpl;

import com.example.SunBase.dtos.CustomerRequestDto;
import com.example.SunBase.dtos.CustomerResponseDto;
import com.example.SunBase.exception.CustomerAlreadyExist;
import com.example.SunBase.exception.CustomerNotFound;
import com.example.SunBase.model.Customer;
import com.example.SunBase.repository.CustomerRepository;
import com.example.SunBase.service.CustomerService;
import com.example.SunBase.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
    @Autowired
    private CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public List<CustomerResponseDto> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerResponseDto>CustomerResponseDtoList=new ArrayList<>();
        for (Customer customer1 : customerList){
            CustomerResponseDtoList.add(CustomerTransformer.CustomerToResponseDto(customer1));
        }
        return CustomerResponseDtoList;
    }

    public List<CustomerResponseDto> searchBy(String attribute, String value){
         List<Customer> customerList=new ArrayList<>();
        if(attribute.equals("First Name")){
             customerList=customerRepository.findAllByFirstName(value);
        }else if(attribute.equals("Last Name")){
            customerList=customerRepository.findAllByLastName(value);
        }else if(attribute.equals("City")){
            customerList=customerRepository.findAllByCity(value);
        }else if(attribute.equals("Email")){
            Customer customer=customerRepository.findByEmail(value).get();
            customerList.add(customer);
        }else if(attribute.equals("Phone")){
            Customer customer=customerRepository.findByPhoneNo(value).get();
            customerList.add(customer);
        }
        List<CustomerResponseDto>ans=new ArrayList<>();
        for(Customer cst:customerList){
            ans.add(CustomerTransformer.CustomerToResponseDto(cst));
        }
        return ans;
    }
    @Override
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExist {
         Optional<Customer> customer1= customerRepository.findByEmail(customer.getEmail());
         if(!customer1.isEmpty()){
             throw new CustomerAlreadyExist("Customer Already Exist In Table");
         }
         Customer saveCustomer = customerRepository.save(customer1.get());
         return saveCustomer;
    }

    @Override
    public CustomerResponseDto getById(int id) throws CustomerNotFound {
        Optional<Customer> customer1=customerRepository.findById(id);
        if(customer1.isEmpty()){
            throw new CustomerNotFound("Customer Not Found");
        }
        CustomerResponseDto customerResponseDto=CustomerTransformer.CustomerToResponseDto(customer1.get());
        return customerResponseDto;
    }

    @Override
    public String delete(int id) throws CustomerNotFound {
        Optional<Customer> customer1=customerRepository.findById(id);
        if(customer1.isEmpty()){
            throw new CustomerNotFound("Customer Not Found");
        }
        customerRepository.delete(customer1.get());
        return "Customer deleted Successfully";
    }

    @Override
    public CustomerResponseDto updateCustomer(CustomerRequestDto customerRequestDto, int id) throws CustomerNotFound {
        Optional<Customer>customerOptional=customerRepository.findById(id);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFound("Customer Not Found");
        }
        Customer customer=customerOptional.get();
        customer.setAddress(customerRequestDto.getAddress());
        customer.setCity(customerRequestDto.getCity());
        customer.setEmail(customerRequestDto.getEmail());
        customer.setState(customerRequestDto.getState());
        customer.setPhoneNo(customerRequestDto.getPhone());
        customer.setFirstName(customerRequestDto.getFirstName());
        customer.setLastName(customerRequestDto.getLastName());
         Customer savedCustomer=customerRepository.save(customer);
        return CustomerTransformer.CustomerToResponseDto(savedCustomer);
    }
}
