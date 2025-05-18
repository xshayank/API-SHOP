package com.avashop10.demo4.Controller;

import com.avashop10.demo4.Entity.Customer;
import com.avashop10.demo4.DTO.CustomerRequest;
import com.avashop10.demo4.DTO.CustomerResponse;
import com.avashop10.demo4.Repository.CustomerRepository;
import com.avashop10.demo4.Error.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public CustomerResponse create(@RequestBody @Valid CustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        Customer saved = customerRepository.save(customer);
        return new CustomerResponse(saved.getId(), saved.getName());
    }

    @GetMapping
    public List<CustomerResponse> all() {
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponse(customer.getId(), customer.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return new CustomerResponse(customer.getId(), customer.getName());
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable Long id, @RequestBody @Valid CustomerRequest dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(dto.getName());
        Customer saved = customerRepository.save(customer);
        return new CustomerResponse(saved.getId(), saved.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}