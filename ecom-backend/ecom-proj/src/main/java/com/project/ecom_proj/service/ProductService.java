package com.project.ecom_proj.service;

import com.project.ecom_proj.model.product;
import com.project.ecom_proj.repo.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private productRepo repo;
    public List<product> getAllProducts()
    {
        return repo.findAll();
    }

    public product getProductById(int id){
     return repo.findById(id).orElse(null);
    }

    public product addProduct(product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }
    public product updateProduct(int id, product product, MultipartFile imageFile) throws IOException {

        product.setId(id);

        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageDate(imageFile.getBytes());
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
        }

        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<product> searchProducts(String keyword) {
        return repo.searchproducts(keyword);

    }
}
