package com.example.shopapp.controller;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.service.Inp.CategoryServiceInp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @Autowired
    CategoryServiceInp categoryServiceInp;

    @PostMapping()
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {
        if (result.hasErrors()) {
            List<String> resultMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            return ResponseEntity.badRequest().body(resultMessage);
        }
        categoryServiceInp.createCategory(categoryDTO);

        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

//    @GetMapping()
//    public ResponseEntity<?> getAllCategoryById(@RequestParam int id){
//        return new ResponseEntity<>("This is get all category with id = "+ id, HttpStatus.OK);
//    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDTO> listCategoryDTO = categoryServiceInp.getAllCategory();

        return new ResponseEntity<>(listCategoryDTO, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id,
                                            @RequestBody CategoryDTO categoryDTO) {
        categoryServiceInp.updateCategory(id, categoryDTO);
        return new ResponseEntity<>("Update category with id: " + id + " successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteCategory(@PathVariable int id) {
        categoryServiceInp.deleteCategory(id);

        return new ResponseEntity<>("Delete category with id: " + id + " successfully", HttpStatus.OK);
    }
}
