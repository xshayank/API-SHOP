package com.avashop10.demo4.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "Customer name must not be blank")
    private String name;
}
