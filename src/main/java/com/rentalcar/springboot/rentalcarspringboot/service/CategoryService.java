package com.rentalcar.springboot.rentalcarspringboot.service;

import com.rentalcar.springboot.rentalcarspringboot.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(int id);

    Category findByTypology(String typology);

    void updateCategory(Category category);

    void deleteCategory(int id);
}
