package com.avashop10.demo4.Repository;

import com.avashop10.demo4.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {}
