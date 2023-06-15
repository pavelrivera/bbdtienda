package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SalesDTO;
import com.example.demo.entity.Category;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.entity.Sales;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ImageService;
import com.example.demo.service.ProductService;
import com.example.demo.service.SaleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productservice;
    private final CategoryService categoryservice;
    private final ImageService imageservice;
    private final SaleService salesservice;

    public ProductController(ProductService productservice, CategoryService categoryservice,
                             ImageService imageservice, SaleService salesservice){
        this.productservice=productservice;
        this.categoryservice=categoryservice;
        this.imageservice=imageservice;
        this.salesservice=salesservice;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDIT')")
    public ProductDTO SaveProduct(@RequestParam("name")String name, @RequestParam("price")float price,
                            @RequestParam("amount")int amount, @RequestParam("category")Long categoryid,
                            @RequestParam("tags")String tags, @RequestParam("description")String description,
                            @RequestParam("information")String information, @RequestParam("assessment")String assessment,
                            @RequestParam("sku")String sku,
                            @RequestParam(value = "files", required = false) MultipartFile[] images){
        Product newproduct= new Product();
        newproduct.setName(name);
        newproduct.setPrice(price);
        newproduct.setAmount(amount);
        Category category = categoryservice.getCategoryById(categoryid);
        newproduct.setCategory(category);
        newproduct.setTags(tags);
        newproduct.setDescription(description);
        newproduct.setInformation(information);
        newproduct.setAssessment(assessment);
        newproduct.setSku(sku);

        Product productSave = productservice.SaveProduct(newproduct);
        if(productSave!=null)
        {
            List<Image> arrImages=imageservice.SaveImage(productSave, images);
            productSave.setImage(arrImages);
            return productservice.convertDTO(productSave);
        }
        else
            return null;

    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {

        Product product= productservice.getProductById(id);
        return productservice.convertDTO(product);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDIT')")
    public String deleteProductById(@PathVariable("id") Long id) throws IOException
    {
        Product delproduct= productservice.getProductById(id);
        List<Image> images = delproduct.getImage();
        if(images.size()>0)
        {
            for (Image image : images) {
                imageservice.deleteImageById(image.getId());
            }
        }

        productservice.deleteProductById(id);
        return "Deleted Successfully";
    }
    
    @GetMapping("/")
    public List<ProductDTO> getAllProducts() {
        List<Product> list= productservice.getAllProduct();
        List<ProductDTO> listnew= new ArrayList<>();
        for (Product product : list) {
            ProductDTO productDTO=productservice.convertDTO(product);
            listnew.add(productDTO);
        }
        return listnew;
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDIT')")
    public ProductDTO updatePeople(@RequestParam("name")String name, @RequestParam("price")float price,
                                @RequestParam("amount")int amount, @RequestParam("category")Long categoryid,
                                @RequestParam("tags")String tags, @RequestParam("description")String description,
                                @RequestParam("information")String information, @RequestParam("assessment")String assessment,
                                @RequestParam("sku")String sku,
                                @RequestParam(value = "files", required = false) MultipartFile[] images,
                                @PathVariable("id") Long id) throws IOException{

        Product newproduct= productservice.getProductById(id);
        newproduct.setName(name);
        newproduct.setPrice(price);
        newproduct.setAmount(amount);
        Category category = categoryservice.getCategoryById(categoryid);
        newproduct.setCategory(category);
        newproduct.setTags(tags);
        newproduct.setDescription(description);
        newproduct.setInformation(information);
        newproduct.setAssessment(assessment);
        newproduct.setSku(sku);

        if(images.length>0)
        {
            for (Image image : newproduct.getImage()) {
                imageservice.deleteImageById(image.getId());
            }
            List<Image> arrImages=imageservice.SaveImage(newproduct, images);
            newproduct.setImage(arrImages);
        }

        productservice.SaveProduct(newproduct);
        return productservice.convertDTO(newproduct);
    }

    @PostMapping("/filter")
    public List<ProductDTO> getAllProductsByFilter(@RequestParam(value = "name", required = false)String name, 
                            @RequestParam(value = "price", required = false)Float price,
                            @RequestParam(value = "amount", required = false)Integer amount, 
                            @RequestParam(value = "category", required = false)String category,
                            @RequestParam(value = "tags", required = false)String tags, 
                            @RequestParam(value = "description", required = false)String description,
                            @RequestParam(value = "information", required = false)String information,
                            @RequestParam(value = "assessment", required = false)String assessment,
                            @RequestParam(value = "sku", required = false)String sku,
                            @RequestParam(value = "page", required = true)Integer page, 
                            @RequestParam(value = "size", required = true)Integer size
                            ) {
        List<Product> list= productservice.getProductByFilter(name, price,amount, category, tags, 
                                                                description, information, assessment,sku);
        List<ProductDTO> listnew= new ArrayList<>();
        for (Product product : list) {
            ProductDTO productDTO=productservice.convertDTO(product);
            listnew.add(productDTO);
        }
        return productservice.pagedResponse(listnew, page, size);
    }

    @PostMapping("/filtercount")
    public int getCountProductsByFilter(@RequestParam(value = "name", required = false)String name, 
                            @RequestParam(value = "price", required = false)Float price,
                            @RequestParam(value = "amount", required = false)Integer amount, 
                            @RequestParam(value = "category", required = false)String category,
                            @RequestParam(value = "tags", required = false)String tags, 
                            @RequestParam(value = "description", required = false)String description,
                            @RequestParam(value = "information", required = false)String information,
                            @RequestParam(value = "assessment", required = false)String assessment,
                            @RequestParam(value = "sku", required = false)String sku
                            ) {
        List<Product> list= productservice.getProductByFilter(name, price,amount, category, tags, 
                                                                description, information, assessment,sku);
        List<ProductDTO> listnew= new ArrayList<>();
        for (Product product : list) {
            ProductDTO productDTO=productservice.convertDTO(product);
            listnew.add(productDTO);
        }
        return listnew.size();
    }

    @PostMapping("/sell")
    public int sellProduct(@RequestParam("id")Long id){//vender producto
        int stock = productservice.sellProduct(id);
        if(stock!=-1)
        {
            Sales sale= new Sales();
            Product product = productservice.getProductById(id);
            sale.setProduct(product);
            salesservice.SaveSales(sale);
        }

        return stock;
    }

    @GetMapping("/allsales")
    public List<SalesDTO> getItemsSold(){//Listado de articulos vendidos
        List<Product> productlist= productservice.getAllProduct();
        List<SalesDTO> listnew= new ArrayList<>();
        for (Product product : productlist) {
            int count =salesservice.getCountSalesByProduct(product);
            if(count>0)
            {
                SalesDTO newsales= new SalesDTO();
                newsales.setAmount(count);
                newsales.setProduct(product);
                listnew.add(newsales);
            }
        }

        return listnew;
    }

    @GetMapping("/totalGain")
    public float getTotalGain(){//Ganancia total
        List<Sales> salesList= salesservice.getAllSales();
        float gain=0;
        for (Sales sale : salesList) {
            gain+=sale.getProduct().getPrice();
        }
        return gain;
    }

    @GetMapping("/allNoStock")
    public List<ProductDTO> noStockProduct(){//Listado de articulos que no tienen stock 
        List<Product> list = productservice.getAllProduct();
        List<ProductDTO> listnew= new ArrayList<>();
        for (Product product : list) {
            if(product.getAmount()==0)
            {
                ProductDTO productDTO=productservice.convertDTO(product);
                listnew.add(productDTO);
            }
        }

        return listnew;
    }

}
