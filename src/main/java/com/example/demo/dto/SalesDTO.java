package com.example.demo.dto;
import com.example.demo.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesDTO {

    private Product product;
    private int amount;
}
