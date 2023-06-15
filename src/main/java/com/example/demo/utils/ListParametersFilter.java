package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.example.demo.entity.Product;

public class ListParametersFilter {
    
    List<Predicate<Product>> filters = new ArrayList<>();
    
    public void addFilter(Predicate<Product> filter)
    {
        filters.add(filter);
    }
    
    public Predicate<Product> getFilter()
    {
        return filters.stream().reduce(x -> true, Predicate::and);
    }
}
