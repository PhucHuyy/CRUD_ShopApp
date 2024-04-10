package com.example.shopapp.service;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.repository.ProductRepository;
import com.example.shopapp.responses.ProductResponse;
import com.example.shopapp.service.Inp.ProductServiceInp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInp {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryID())
                .orElseThrow(() -> new DataNotFoundException("Category id not exist!"));

        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setPrice((float) productDTO.getPrice());
        newProduct.setThumbnail(productDTO.getThumbnail());
        newProduct.setDesc(productDTO.getDescription());
        newProduct.setCategory(existingCategory);

        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }

    @Override
    public Product getProductById(int productId) throws Exception {

        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + productId));
    }

    @Override
    public Product updateProduct(int productId, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(productId);

        if (existingProduct != null) {
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryID())
                    .orElseThrow(() -> new DataNotFoundException("Category id not exist!"));

            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice((float) productDTO.getPrice());
            existingProduct.setDesc(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setCategory(existingCategory);
            return productRepository.save(existingProduct);
        }

        return null;
    }

    @Override
    public void deleteProduct(int productId) throws Exception {
        productRepository.deleteById(productId);

    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }


}
