package com.example.dbtools.controllers;

import com.example.dbtools.model.Customer;
import com.example.dbtools.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DbController {

    CustomerService customerService;

    public DbController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<Customer> getall() {
        return customerService.getAll();
    }
}
