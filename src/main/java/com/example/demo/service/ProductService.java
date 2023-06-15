package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.springframework.stereotype.Service;


import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.ListParametersFilter;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageservice;
    private final CategoryService categoryservice;


    public Product SaveProduct(Product product){
        return productRepository.save(product);
    }
    
    public Product getProductById(Long id){
        Product product = new Product();
        product = productRepository.findById(id).get();
        return product;
    }

    public Product getProductBySKU(String sku){
        List<Product> productList = productRepository.findAll();
        Product product = null;
        for (Product objproduct: productList) {
            if(objproduct.getSku()==sku){
                product=objproduct;
            }
        }
        return product;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) throws IOException{
        Product productdb = productRepository.findById(id).get();
        if(productdb!=null)
        {
            for (Image image : productdb.getImage()) {
                imageservice.deleteImageById(image.getId());             
            }
        }
         
        productRepository.deleteById(id);
    }

    public ProductDTO convertDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        if(product!=null)
        {
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setCategory(product.getCategory());
            productDTO.setTags(product.getTags());
            productDTO.setDescription(product.getDescription());
            productDTO.setInformation(product.getInformation());
            productDTO.setAssessment(product.getAssessment());
            productDTO.setSku(product.getSku());
            List<ImageDTO> imageslist= new ArrayList<>();
            for (Image image : product.getImage()) {
                ImageDTO imageDTO= new ImageDTO();
                imageDTO.setId(image.getId());
                String urlimage= imageservice.getUrlByImage(image.getId());
                imageDTO.setUrl(urlimage);
                imageslist.add(imageDTO);
            }
            productDTO.setImage(imageslist);           
        }
        
        return productDTO;
    }

    public int sellProduct(Long id){
        int stock=-1;
        Product product = this.getProductById(id);
        if(product.getAmount()>0)
        {
            int amount = product.getAmount()-1;
            product.setAmount(amount);
            this.SaveProduct(product);
            stock=amount;
        }

        return stock;
    }

    public List<Product> getProductByFilter(String name, Float price, Integer amount, String category,
                                            String tags, String description, String information, 
                                            String assessment, String sku){
        List<Product> productsdb = productRepository.findAll();
        List<Product> productsnew = new ArrayList<>();

        ListParametersFilter filters = new ListParametersFilter();

        if(name!=null && !name.isEmpty())
            filters.addFilter(p -> p.getName().toLowerCase().contains(name.toLowerCase()));

        if(price!=null)
            filters.addFilter(p -> p.getPrice().compareTo(price)==0);

        if(amount!=null)
            filters.addFilter(p -> p.getAmount()==amount);

        if(category!=null && !category.isEmpty())
            filters.addFilter(p -> p.getCategory().getName().toLowerCase().contains(category.toLowerCase()));

        if(tags!=null && !tags.isEmpty())
            filters.addFilter(p -> p.getTags().toLowerCase().contains(tags.toLowerCase()));

        if(description!=null && !description.isEmpty())
            filters.addFilter(p -> p.getDescription().toLowerCase().contains(description.toLowerCase()));

        if(information!=null && !information.isEmpty())
            filters.addFilter(p -> p.getInformation().toLowerCase().contains(information.toLowerCase()));

        if(assessment!=null && !assessment.isEmpty())
            filters.addFilter(p -> p.getAssessment().toLowerCase().contains(assessment.toLowerCase()));

        if(sku!=null && !sku.isEmpty())
            filters.addFilter(p -> p.getSku().toLowerCase().contains(sku.toLowerCase()));

        
        productsdb.stream().filter(filters.getFilter()).forEach((p) -> {
            productsnew.add(p);
        });                                       
        

        return productsnew;
    }

    public List<ProductDTO> pagedResponse(List<ProductDTO> allItems, int page, int limit){
    int totalItems = allItems.size();
    int fromIndex = page*limit;
    int toIndex = fromIndex+limit;
    if(fromIndex <= totalItems) {
        if(toIndex > totalItems){
            toIndex = totalItems;
        }
        return allItems.subList(fromIndex, toIndex);
    }else {
        return allItems;
    }
}

    public void initialCharge() {
        Product newproduct1= new Product();
        newproduct1.setName("tenis nike");
        newproduct1.setPrice((float)50.99);
        newproduct1.setAmount(2);
        Category category = categoryservice.getCategoryById((long) 3);
        newproduct1.setCategory(category);
        newproduct1.setTags("zapatos");
        newproduct1.setDescription("color negro");
        newproduct1.setInformation("importacion");
        newproduct1.setAssessment("excelente calidad");
        newproduct1.setSku("1111");
        productRepository.save(newproduct1);

        Product newproduct2= new Product();
        newproduct2.setName("tenis adidas");
        newproduct2.setPrice((float)30.99);
        newproduct2.setAmount(2);
        Category category2 = categoryservice.getCategoryById((long) 3);
        newproduct2.setCategory(category2);
        newproduct2.setTags("zapatos");
        newproduct2.setDescription("color blanco");
        newproduct2.setInformation("importacion");
        newproduct2.setAssessment("excelente calidad");
        newproduct2.setSku("1112");
        productRepository.save(newproduct2);

        Product newproduct3= new Product();
        newproduct3.setName("leche en polvo");
        newproduct3.setPrice((float)9.99);
        newproduct3.setAmount(1);
        Category category3 = categoryservice.getCategoryById((long) 1);
        newproduct3.setCategory(category3);
        newproduct3.setTags("zapatos");
        newproduct3.setDescription("bolsa en polvo");
        newproduct3.setInformation("producto nacional");
        newproduct3.setAssessment("baja calidad");
        newproduct3.setSku("1113");
        productRepository.save(newproduct3);

    }

}
