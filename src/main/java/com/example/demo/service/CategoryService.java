package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public Category SaveCategory(Category category){
        return categoryRepository.save(category);
    }
    
    public Category getCategoryById(Long id){
        Category category = new Category();
        category = categoryRepository.findById(id).get();
        return category;
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public String deleteCategoryById(Long id){
        this.categoryRepository.deleteById(id);
        return "ok";
    }

    public void initialCharge() {
        Category newcat1= new Category();
        newcat1.setName("alimentos");
        categoryRepository.save(newcat1);
        
        Category newcat2= new Category();
        newcat2.setName("ferreteria");
        categoryRepository.save(newcat2);

        Category newcat3= new Category();
        newcat3.setName("zapatos");
        categoryRepository.save(newcat3);
    }

}
