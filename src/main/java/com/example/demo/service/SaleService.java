package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.Sales;
import com.example.demo.repository.SalesRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class SaleService {
    private final SalesRepository salesRepository;


    public Sales SaveSales(Sales sales){
        return salesRepository.save(sales);
    }
    
    public Sales getSalesById(Long id){
        Sales sales = new Sales();
        sales = salesRepository.findById(id).get();
        return sales;
    }

    public List<Sales> getAllSales(){
        return salesRepository.findAll();
    }

    public int getCountSalesByProduct(Product product){
        int count=0;
        List<Sales> salesList= salesRepository.findAll();

        for (Sales sale : salesList) {
            if(sale.getProduct().getId()==product.getId())
                count++;
        }
        return count;
    }

}
