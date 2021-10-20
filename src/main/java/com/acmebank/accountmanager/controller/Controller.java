package com.acmebank.accountmanager.controller;

import com.acmebank.accountmanager.model.AuthenticationRequest;
import com.acmebank.accountmanager.model.AuthenticationResponse;
import com.acmebank.accountmanager.model.Customer;
import com.acmebank.accountmanager.model.TransferRequest;
import com.acmebank.accountmanager.repo.CustomerRepo;
import com.acmebank.accountmanager.service.JwtService;
import com.acmebank.accountmanager.service.MyUserDetailsService;
import com.acmebank.accountmanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    TransactionService transactionService;


    @GetMapping("/balance")
    public ResponseEntity<?> balance(){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long accountID = Long.parseLong(((UserDetails) principal).getUsername());
            return ResponseEntity.ok(transactionService.getBalance(accountID));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long accountID = Long.parseLong(((UserDetails) principal).getUsername());
            transactionService.transfer(accountID, request.getToAccount(), request.getAmount());
            return ResponseEntity.ok("SUCCESS");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Wrong Username/Password", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
