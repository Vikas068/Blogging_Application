package com.backend.blogging.repository;

import com.backend.blogging.entities.CategoryId;
import com.backend.blogging.entities.Post;
import com.backend.blogging.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> getAllByUser(User user);
    List<Post> findByCategoryId(CategoryId categoryId);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title );
}
