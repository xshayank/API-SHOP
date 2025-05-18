package com.avashop10.demo4.Repository;

import com.avashop10.demo4.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {}
