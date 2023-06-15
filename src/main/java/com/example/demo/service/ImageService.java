package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demo.controller.ImageController;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.repository.ImageRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.nio.file.*;
import java.text.SimpleDateFormat;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final String rutaImgenes="src//main//resources//static//images";

    public String getNameImage(String realname){
		Date c = Calendar.getInstance().getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd-H-mm-ss");
		String dateformated = format1.format(c);
		String name = realname.substring(0,realname.length()-4)+ dateformated +realname.substring(realname.length()-4,realname.length());
        return name;
    }

    public List<Image> SaveImage(Product product,MultipartFile[] images){
        List<Image> arrImages = new ArrayList<>();
        if(images!=null)
        {
            Path directorioimagenes = Paths.get(rutaImgenes);
            String rutaAbsoluta= directorioimagenes.toFile().getAbsolutePath();
            for (MultipartFile file : images) {
                try {
                    byte[] bytesimg=file.getBytes();
                    String nameimg=this.getNameImage(file.getOriginalFilename());
                    Path rutaCompleta=Paths.get(rutaAbsoluta+"//"+nameimg);
                    Files.write(rutaCompleta, bytesimg); 
                    Image image = new Image();
                    image.setName(nameimg);
                    image.setProduct(product);
                    imageRepository.save(image);
                    arrImages.add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }			
		    }
        }

        return arrImages;
    }
    
    public Image getImageById(Long id){
        Image image = new Image();
        image = imageRepository.findById(id).get();
        return image;
    }

    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }

    public String deleteImageById(Long id) throws IOException{
        Image imagedb = getImageById(id);
        if(imagedb!=null)
        {
            Path path = Paths.get(rutaImgenes).resolve(imagedb.getName());
            File thedir= new File(path.toString());
            if(thedir.exists())
            {
                Files.delete(path);
                imageRepository.deleteById(id);
                return "ok";
            }   
        }
        return "image not found";
        
    }

    public ResponseEntity<?> getImageFileById(Long id){
        Image imagedb = getImageById(id);
        if(imagedb!=null)
        {
            Resource resource = null;
            try {
                String filename= imagedb.getName();
                Path file = Paths.get(rutaImgenes).resolve(filename);
                File theDir = new File(file.toString());
                if(theDir.exists())
                    resource = new UrlResource(file.toUri());
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
            
            if (resource == null) {
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            }
            
            String contentType = "application/octet-stream";
            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        }
        return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
    }

    public String getUrlByImage(Long id) {
      String url = MvcUriComponentsBuilder
          .fromMethodName(ImageController.class, "getFile", id).build().toString();
      return url;

    }

}
