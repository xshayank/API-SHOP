package com.avashop10.demo4.DTO;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Long customerId;
    private List<Long> productIds;

    public OrderResponse(Long id, Long customerId, List<Long> productIds) {
        this.id = id;
        this.customerId = customerId;
        this.productIds = productIds;
    }
}