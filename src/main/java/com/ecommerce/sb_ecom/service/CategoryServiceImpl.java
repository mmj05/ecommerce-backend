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
}
