package com.backend.blogging.controller;

import com.backend.blogging.entities.Post;
import com.backend.blogging.entities.User;
import com.backend.blogging.payload.ApiResponse;
import com.backend.blogging.payload.PostResponse;
import com.backend.blogging.service.FileService;
import com.backend.blogging.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("user/{userid}/category/{catId}/posts")
    public ResponseEntity<Post> savePosts(@RequestBody Post post, @PathVariable int userid,@PathVariable int catId) throws Exception {
        Post savePost=postService.createPost(post,userid,catId);
        return new ResponseEntity<>(savePost, HttpStatus.CREATED);
    }

    @GetMapping("user/{userid}/post")
    public ResponseEntity<List<Post>> getPostByUser(@PathVariable int userid)
    {
        List<Post> arrListUser=this.postService.getPostByUser(userid);
        return new ResponseEntity<List<Post>>(arrListUser,HttpStatus.OK);
    }

    @GetMapping("user/getAllPosts")
    public ResponseEntity<PostResponse> getListOfPosts(@RequestParam int pagenumber,
                                                       @RequestParam int pageSize,
                                                       @RequestParam String title,
                                                       @RequestParam String sortDir)
    {
        return new ResponseEntity<>(this.postService.getPostList(pagenumber,pageSize,title,sortDir), HttpStatus.OK);
    }

    @GetMapping("category/{catId}/post")
    public ResponseEntity<List<Post>> getPostBycategory(@PathVariable int catId)
    {
        List<Post> arrListCategory=postService.getPostByCategory(catId);
        return new ResponseEntity<>(arrListCategory,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable int postId)
    {
     Post getPost=postService.getPostById(postId);
     return new ResponseEntity<>(getPost,HttpStatus.OK);
    }

    @GetMapping("delete/{postId}")
    public ApiResponse deletePost(@PathVariable int postId)
    {
        postService.deletePost(postId);
        return new ApiResponse("Post deleted successfully",true);
    }

    @PutMapping("/put/{postId}")
    public ResponseEntity<Post>  updatePost(@RequestBody Post post, @PathVariable int postId, BindingResult bindingResult)
    {
       // Post updatePost=postService.updatePost(post,postId);
        return new ResponseEntity<>(postService.updatePost(post,postId),HttpStatus.OK);
    }

    @GetMapping("/post/search/{keys}")
    public ResponseEntity<List<Post>> searchPostByKey(@PathVariable String keys)
    {
        List<Post> result=postService.searchPosts(keys);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/post/image/upload/{postId}")
    public ResponseEntity<Post> uploadPostImg(@RequestParam("img")MultipartFile img,@PathVariable int postId) throws IOException {
        Post updatePost=this.postService.getPostById(postId);
        String file=this.fileService.uploadImage(path,img);
        updatePost.setImageName(file);
        Post updatePost1=this.postService.updatePost(updatePost,postId);
        return new ResponseEntity<Post>(updatePost1,HttpStatus.OK);

    }

    @GetMapping(value = "/post/image/{img}",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public void getPostImg(@PathVariable String img, HttpServletResponse response) throws IOException {
        InputStream is=this.fileService.getResource(path,img);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is,response.getOutputStream());
    }

}
