package com.acmebank.accountmanager.service;

import com.acmebank.accountmanager.model.Customer;
import com.acmebank.accountmanager.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TransactionService {
    @Autowired CustomerRepo customerRepo;

    private Customer getCustomer(Long id) throws Exception {
        Customer customer = customerRepo.findById(id).orElseThrow(()-> new Exception("Customer(" +id+")  Not Found"));
        return customer;
    }
    public BigDecimal getBalance(Long id) throws Exception{
        return getCustomer(id).getBalance();
    }
    @Transactional
    public Boolean transfer(Long fromId, Long toId, BigDecimal amount) throws Exception {
        Customer fromCustomer = getCustomer(fromId);
        if(fromCustomer.getBalance().compareTo(amount) < 0)
            throw new Exception("Insufficient Balance");
        Customer toCustomer = getCustomer(toId);
        fromCustomer.setBalance(fromCustomer.getBalance().subtract(amount));
        toCustomer.setBalance(toCustomer.getBalance().add(amount));
        return true;
    }
}
