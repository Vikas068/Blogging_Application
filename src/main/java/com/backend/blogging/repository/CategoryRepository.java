package com.backend.blogging.repository;

import com.backend.blogging.entities.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryId,Integer> {

}
