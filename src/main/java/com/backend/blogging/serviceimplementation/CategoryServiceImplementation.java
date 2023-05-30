package com.backend.blogging.serviceimplementation;

import com.backend.blogging.entities.CategoryId;
import com.backend.blogging.repository.CategoryRepository;
import com.backend.blogging.repository.UserRepository;
import com.backend.blogging.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

/*    //model mapper helps in mapping between the dto and entity so ORM can be success.
    @Autowired
    private ModelMapper modelMapper;*/

    @Override
    public CategoryId createCategory(CategoryId categoryId) {
        return categoryRepository.save(categoryId);
    }

    @Override
    public CategoryId updateCategory(CategoryId categoryId, Integer id) {
        CategoryId saveCategoryId=categoryRepository.findById(id).get();
        saveCategoryId.setCategoryTitle(categoryId.getCategoryTitle());
        saveCategoryId.setCategoryDescription(categoryId.getCategoryDescription());
        saveCategoryId.setPosts(categoryId.getPosts());
        return saveCategoryId;
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryId getCategory(int id) {

        return categoryRepository.findById(id).get();
    }

    @Override
    public List<CategoryId> getAllCategories() {
        return categoryRepository.findAll();
    }
}