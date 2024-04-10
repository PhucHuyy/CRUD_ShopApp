package com.example.shopapp.service.Inp;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductServiceInp {
    Product createProduct(ProductDTO productDTO) throws Exception;

    Page<ProductResponse> getAllProduct(PageRequest pageRequest);

    Product getProductById(int productId) throws Exception;
    Product updateProduct(int productId, ProductDTO productDTO) throws Exception;
    void deleteProduct(int productId) throws Exception;
    boolean existsByName(String name);
//    ProductImage createProductImage(
//            Long productId,
//            ProductImageDTO productImageDTO) throws Exception;
}
