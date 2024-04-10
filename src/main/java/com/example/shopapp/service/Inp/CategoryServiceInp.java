package com.example.shopapp.service.Inp;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryServiceInp {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(int categoryID);
    List<CategoryDTO> getAllCategory();
    Category updateCategory(int categoryID, CategoryDTO categoryDTO);
    void deleteCategory(int id);

}
