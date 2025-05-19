package com.avashop10.demo4.Repository;

import com.avashop10.demo4.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods can be defined here if needed
    // For example, findByCategory, findByPriceRange, etc.
    Page<Product> findByNameContainingIgnoreCase(String name, org.springframework.data.domain.Pageable pageable);
}
