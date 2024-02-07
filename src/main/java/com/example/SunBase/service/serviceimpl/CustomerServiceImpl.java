package com.example.SunBase.service.serviceimpl;

import com.example.SunBase.dtos.CustomerRequestDto;
import com.example.SunBase.dtos.CustomerResponseDto;
import com.example.SunBase.dtos.TestRequestDto;
import com.example.SunBase.exception.CustomerAlreadyExist;
import com.example.SunBase.exception.CustomerNotFound;
import com.example.SunBase.model.Customer;
import com.example.SunBase.repository.CustomerRepository;
import com.example.SunBase.service.CustomerService;
import com.example.SunBase.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
//    @Autowired
//    RestTemplate restTemplate;
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

    public List<CustomerResponseDto> searchBy( String attribute, String value){
         List<Customer> customerList=new ArrayList<>();
        if(attribute.equals("firstName")){
             customerList=customerRepository.findAllByFirstName(value);
        }else if(attribute.equals("lastName")){
            customerList=customerRepository.findAllByLastName(value);
        }else if(attribute.equals("city")){
            customerList=customerRepository.findAllByCity(value);
        }else if(attribute.equals("state")){
            customerList=customerRepository.findAllByState(value);
        }else if(attribute.equals("email")){
            Customer customer=customerRepository.findByEmail(value);
            customerList.add(customer);
        }else if(attribute.equals("phoneNo")){
            Customer customer=customerRepository.findByPhoneNo(value);
            customerList.add(customer);
        }
        List<CustomerResponseDto>ans=new ArrayList<>();
        for(Customer cst:customerList){
            ans.add(CustomerTransformer.CustomerToResponseDto(cst));
        }
        return ans;
    }

    /*@Override
    public String upadateSync(TestRequestDto testRequestDto) {
        String url="https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";
        Object obj= restTemplate.postForEntity(url, testRequestDto, Object.class);
        System.out.println(obj);
        return "AABSH";
    }*/

    @Override
    public CustomerResponseDto addCustomer(Customer customer) throws CustomerAlreadyExist {
         Customer customer1= customerRepository.findByEmail(customer.getEmail());
         if(customer1!=null){
             throw new CustomerAlreadyExist("Customer Already Exist In Table");
         }
         Customer saveCustomer = customerRepository.save(customer);
         return CustomerTransformer.CustomerToResponseDto(saveCustomer);
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
    public String delete(String email) throws CustomerNotFound {
        Customer customer1=customerRepository.findByEmail(email);
        if(customer1==null){
            throw new CustomerNotFound("Customer Not Found");
        }
        customerRepository.delete(customer1);
        return "Customer deleted Successfully";
    }

    @Override
    public CustomerResponseDto updateCustomer(CustomerRequestDto customerRequestDto, String email) throws CustomerNotFound {
        Customer customerOptional=customerRepository.findByEmail(email);
        if(customerOptional==null){
            throw new CustomerNotFound("Customer Not Found");
        }

        customerOptional.setAddress(customerRequestDto.getAddress());
        customerOptional.setCity(customerRequestDto.getCity());
        customerOptional.setEmail(customerRequestDto.getEmail());
        customerOptional.setState(customerRequestDto.getState());
        customerOptional.setPhoneNo(customerRequestDto.getPhone());
        customerOptional.setFirstName(customerRequestDto.getFirstName());
        customerOptional.setLastName(customerRequestDto.getLastName());
         Customer savedCustomer=customerRepository.save(customerOptional);
        return CustomerTransformer.CustomerToResponseDto(savedCustomer);
    }
}
