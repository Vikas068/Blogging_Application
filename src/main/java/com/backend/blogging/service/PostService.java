package com.backend.blogging.service;

import com.backend.blogging.entities.Post;
import com.backend.blogging.payload.PostResponse;
import org.hibernate.event.spi.PostDeleteEvent;

import java.util.List;

public interface PostService {

    Post createPost(Post post,int userId,int categoryId) throws Exception;

    Post updatePost(Post post,int postId);

    void deletePost(int postId);

    PostResponse getPostList(int pageSize, int pageNumber,String title,String sortDir);

    Post getPostById(int postId);

    List<Post> getPostByCategory(int categoryId);

    List<Post> getPostByUser(int userId);

    List<Post> searchPosts(String keyword);
}
