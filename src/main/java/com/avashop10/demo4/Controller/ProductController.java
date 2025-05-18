package com.avashop10.demo4.Controller;

import com.avashop10.demo4.DTO.ProductRequest;
import com.avashop10.demo4.DTO.ProductResponse;
import com.avashop10.demo4.Entity.Product;
import com.avashop10.demo4.Repository.ProductRepository;
import com.avashop10.demo4.Error.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ProductResponse create(@RequestBody @Valid ProductRequest dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        Product saved = productRepository.save(product);
        return new ProductResponse(saved.getId(), saved.getName(), saved.getPrice());
    }

    @GetMapping
    public List<ProductResponse> all() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody @Valid ProductRequest dto) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        Product saved = productRepository.save(p);
        return new ProductResponse(saved.getId(), saved.getName(), saved.getPrice());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}