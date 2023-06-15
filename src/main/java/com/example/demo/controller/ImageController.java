package com.example.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.ImageService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageservice;

    public ImageController(ImageService imageservice){
        this.imageservice=imageservice;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        return imageservice.getImageFileById(id);
    }
    

}
