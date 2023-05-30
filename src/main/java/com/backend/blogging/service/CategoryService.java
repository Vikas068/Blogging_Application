package com.backend.blogging.service;

import com.backend.blogging.entities.CategoryId;

import java.util.List;

public interface CategoryService {

    CategoryId createCategory(CategoryId categoryId);

    CategoryId updateCategory(CategoryId categoryId,Integer id);

    void deleteCategory(int id);

    CategoryId getCategory(int id);

    List<CategoryId> getAllCategories();


}
