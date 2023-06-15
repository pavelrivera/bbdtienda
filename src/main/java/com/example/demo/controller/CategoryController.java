package com.example.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryservice;

    public CategoryController(CategoryService categoryservice){
        this.categoryservice=categoryservice;
    }

    @GetMapping("/")
    public List<Category> getAllCategory() {
        return categoryservice.getAllCategory();
    }

}
