package com.avashop10.demo4.Controller;


import com.avashop10.demo4.DTO.OrderRequest;
import com.avashop10.demo4.DTO.OrderResponse;
import com.avashop10.demo4.Entity.Order;
import com.avashop10.demo4.Entity.Customer;
import com.avashop10.demo4.Entity.Product;
import com.avashop10.demo4.Repository.OrderRepository;
import com.avashop10.demo4.Repository.CustomerRepository;
import com.avashop10.demo4.Repository.ProductRepository;
import com.avashop10.demo4.Error.OrderNotFoundException;
import com.avashop10.demo4.Error.CustomerNotFoundException;
import com.avashop10.demo4.Error.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public OrderResponse create(@RequestBody @Valid OrderRequest dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.getCustomerId()));

        List<Product> products = productRepository.findAllById(dto.getProductIds());
        if (products.size() != dto.getProductIds().size()) {
            throw new ProductNotFoundException(-1L); // You can customize this to list missing products.
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        Order saved = orderRepository.save(order);

        List<Long> productIds = saved.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return new OrderResponse(saved.getId(), customer.getId(), productIds);
    }

    @GetMapping
    public List<OrderResponse> all() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getCustomer().getId(),
                        order.getProducts().stream().map(Product::getId).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponse get(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        List<Long> productIds = order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        return new OrderResponse(order.getId(), order.getCustomer().getId(), productIds);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
