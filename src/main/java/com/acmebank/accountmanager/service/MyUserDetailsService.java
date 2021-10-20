package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.model.Customer;
import com.acmebank.accountmanager.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerRepo customerRepo;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(Long.parseLong(s));
        if(!customerOptional.isPresent()){
            throw new UsernameNotFoundException("username not found");
        }
        Customer customer = customerOptional.get();
        return new User(customer.getId().toString(),customer.getPassword(),new ArrayList<>());
    }
}
