package com.project.ecom_proj.controller;

import com.project.ecom_proj.model.product;
import com.project.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class productController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<product>>getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);


    }
    @GetMapping("/product/{id}")
    public ResponseEntity<product> getProduct(@PathVariable int id){

          product product =service.getProductById(id);
          if(product !=null)
              return new ResponseEntity<>(product,HttpStatus.OK);
          else
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart product product,@RequestPart MultipartFile imageFile){
       try {
           product product1 = service.addProduct(product, imageFile);
           return new ResponseEntity<>(product1, HttpStatus.CREATED);
       }
       catch(Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        product product = service.getProductById(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable int id,
            @RequestPart product product,
            @RequestPart(required = false) MultipartFile imageFile) {

        product product1 = null;

        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }

        if (product1 != null)
            return new ResponseEntity<>("updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        product product = service.getProductById(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);


    }
    @GetMapping("/products/search")
    public ResponseEntity<List<product>> searchProducts(@RequestParam String keyword){
        System.out.println("searching with"+keyword);
        List<product> products =  service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }
}

