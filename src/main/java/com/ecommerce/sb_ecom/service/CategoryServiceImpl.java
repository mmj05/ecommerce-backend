package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> catagories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return catagories;
    }

    @Override
    public void createCategory(Category category) {
        catagories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category  category = catagories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId)).findFirst().orElse(null);

        if (category == null) {
            return "Category with categoryId: " + categoryId + " not found";
        }

        catagories.remove(category);

        return "Category with categoryId: " + categoryId + " deleted successfully";
    }
}
