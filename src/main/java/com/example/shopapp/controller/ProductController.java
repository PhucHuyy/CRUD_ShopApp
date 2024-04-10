package com.example.shopapp.controller;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.responses.ProductListResponse;
import com.example.shopapp.responses.ProductResponse;
import com.example.shopapp.service.Inp.ProductServiceInp;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    ProductServiceInp productServiceInp;


    @GetMapping()
    public ResponseEntity<?> getAllProducts(@RequestParam int page, @RequestParam int limit) {

        PageRequest pageRequest = PageRequest.of(page,limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productServiceInp.getAllProduct(pageRequest);
        // lấy tổng số trang
        int totalPage = productPage.getTotalPages();

        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .totalPage(totalPage)
                .productResponses(products)
                .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") int productId){
        try {
            Product existingProduct = productServiceInp.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) throws Exception {
        productServiceInp.createProduct(productDTO);

        return new ResponseEntity<>("Create product successfully. " + "\n" + productDTO, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProducts(
            @Valid ProductDTO productDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> resultMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(resultMessage);
        }

        // upload file
        List<MultipartFile> files = productDTO.getFiles();
        files = files == null ? new ArrayList<>() : files;

        for (MultipartFile dataFile : files) {
            if (dataFile.getSize() == 0)
                continue;

            String contentType = dataFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return new ResponseEntity<>("File must be an image", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }

            // save file and update thumnail in DTO
            try {
                String filename = storeFile(dataFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // save to DB

        }


        return new ResponseEntity<>("successfully", HttpStatus.OK);
    }

    private String storeFile(MultipartFile file) throws IOException {
        // put original file name
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        // update unique filename
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        // create folder -> save to
        Path uploadFile = Paths.get("uploads");

        //check folder exist
        if (!Files.exists(uploadFile)) {
            Files.createDirectories(uploadFile);
        }

        // create path full -> file
        Path destination = Paths.get(uploadFile.toString(), uniqueFilename);

        // copy file to folder created
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int productId,
                                           @Valid @RequestBody ProductDTO productDTO) throws Exception {
        productServiceInp.updateProduct(productId, productDTO);

        return new ResponseEntity<>("Update product successfully with id = " + productId, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int productId) throws Exception {
        productServiceInp.deleteProduct(productId);
        return new ResponseEntity<>("delete product with id = " + productId + " successfully", HttpStatus.OK);
    }

    //@PostMapping("/generateFakeProducts")
    public ResponseEntity<?> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            String productName = faker.commerce().productName();
            if (productServiceInp.existsByName(productName)) {
                continue;
            }

            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price(faker.number().numberBetween(10, 12345678))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryID(faker.number().numberBetween(3,4))
                    .build();
            try{
                productServiceInp.createProduct(productDTO);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }
        return new ResponseEntity<>("fake successfully", HttpStatus.OK);
    }
}
