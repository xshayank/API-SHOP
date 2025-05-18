package com.avashop10.demo4.DTO;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;

    public ProductResponse(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
