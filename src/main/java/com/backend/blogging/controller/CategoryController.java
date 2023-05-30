package com.backend.blogging.controller;

import com.backend.blogging.entities.CategoryId;
import com.backend.blogging.repository.CategoryRepository;
import com.backend.blogging.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/")
    public ResponseEntity<CategoryId> createCategory(@Valid @RequestBody CategoryId category) {
        CategoryId categoryId=categoryService.createCategory(category);
        return new ResponseEntity(categoryId, HttpStatus.CREATED);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<CategoryId> getCategory()
    {
        return new ResponseEntity(categoryService.getAllCategories(),HttpStatus.OK);
    }

    @PutMapping("/update/{catId}")
    public ResponseEntity<CategoryId> getUpdateCategory(@PathVariable int userId,@RequestBody CategoryId categoryId)
    {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId,userId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{catId}")
    public void deleteCategory(@PathVariable int userId)
    {
        categoryService.deleteCategory(userId);
    }

    @GetMapping("/get/categoryById/{catId}")
    public ResponseEntity<CategoryId> getCategoryById(@PathVariable int userId)
    {
        return new ResponseEntity<>(categoryService.getCategory(userId),HttpStatus.OK);
    }
}
