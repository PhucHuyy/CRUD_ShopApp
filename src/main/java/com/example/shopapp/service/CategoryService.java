package com.example.shopapp.service;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.models.Category;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.service.Inp.CategoryServiceInp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceInp {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getCategoryName())
                .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(int categoryID) {
        return categoryRepository.findById(categoryID)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("id"));
        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);

        for(Category data: categoryPage){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(data.getId());
            categoryDTO.setCategoryName(data.getName());

            categoryDTOS.add(categoryDTO);
        }

        return categoryDTOS;
    }

    @Override
    public Category updateCategory(int categoryID, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryID);
        existingCategory.setName(categoryDTO.getCategoryName());
        categoryRepository.save(existingCategory);

        return existingCategory;

    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
