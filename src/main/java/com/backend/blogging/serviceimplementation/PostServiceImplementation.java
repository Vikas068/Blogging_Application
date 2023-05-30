package com.backend.blogging.serviceimplementation;

import com.backend.blogging.entities.CategoryId;
import com.backend.blogging.entities.Post;
import com.backend.blogging.entities.User;
import com.backend.blogging.payload.PostResponse;
import com.backend.blogging.repository.CategoryRepository;
import com.backend.blogging.repository.PostRepository;
import com.backend.blogging.repository.UserRepository;
import com.backend.blogging.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Post createPost(Post post, int userId, int categoryId) throws Exception {
        User user=this.userRepository.findById(userId).orElseThrow(()->new Exception("User data not found."));
        CategoryId category=this.categoryRepository.findById(categoryId).orElseThrow(()->new Exception("Category data not found."));

        post.setUser(user);
        post.setCategoryId(category);
        post.setAddedDate(new Date());
        post.setImageName("default.png");
        Post returnPost=this.postRepository.save(post);
        return returnPost;
    }

    @Override
    public Post updatePost(Post post, int postId) {
        Post savePost=this.postRepository.findById(postId).orElse(null);
        savePost.setContent(post.getContent());
        savePost.setComment(post.getComment());
        savePost.setImageName(post.getImageName());
        Post updateData=postRepository.save(savePost);
        return updateData;
    }

    @Override
    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostResponse getPostList(int pageSize, int pageNumber,String title,String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("ASC")?Sort.by(title).ascending():Sort.by(title).descending());
       /* if(sortDir.equalsIgnoreCase("ASC"))
        {
         sort=Sort.by(title).ascending();
        }
        else{
            sort=Sort.by(title).descending();
        }*/
        Pageable pages= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> getPages=this.postRepository.findAll(pages);

        List<Post> listPosts=getPages.getContent();
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(listPosts);
        postResponse.setLastPage(getPages.isLast());
        postResponse.setPageNumber(getPages.getNumber());
        postResponse.setPageSize(getPages.getSize());
        postResponse.setTotalElements(getPages.getTotalElements());
        postResponse.setTotalPages(getPages.getTotalPages());
        return postResponse;
    }

    @Override
    public Post getPostById(int postId) {
        return postRepository.findById(postId).get();
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        CategoryId category=this.categoryRepository.findById(categoryId).get();
        List<Post> postList=this.postRepository.findByCategoryId(category).stream().collect(Collectors.toList());

        List<Post> getAllPost=postList.stream().collect(Collectors.toList());
        return getAllPost;
    }

    @Override
    public List<Post> getPostByUser(int userId) {

        User user=this.userRepository.findById(userId).get();

        List<Post> getPostList=this.postRepository.getAllByUser(user);

        return getPostList;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        List<Post> findPostSearch=this.postRepository.searchByTitle("%"+keyword+"%");
        return findPostSearch;
    }
}
