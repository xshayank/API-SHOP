package com.avashop10.demo4.Repository;

import com.avashop10.demo4.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {}
