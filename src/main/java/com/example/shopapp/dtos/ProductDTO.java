package com.example.shopapp.dtos;


import com.example.shopapp.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @Size(min = 3, max = 200, message = "name must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "price must be greater than or equal 0")
    @Max(value = 10000000, message = "price must be less than or equal 10,000,000")
    private double price;

    private String thumbnail;

    private String description;

    private int categoryID;
//    private CategoryDTO categoryDTO;

    private List<MultipartFile> files;
}
