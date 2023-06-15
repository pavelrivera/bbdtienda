package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private long id;
    private String name;
    private float price;
    private int amount;
    private Category category;
    private String tags;
    private String description;
    private String information;
    private String assessment;
    private String sku;
    private List<ImageDTO> image;
}
