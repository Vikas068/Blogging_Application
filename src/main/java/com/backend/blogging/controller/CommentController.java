package com.backend.blogging.controller;

import com.backend.blogging.entities.Comment;
import com.backend.blogging.payload.ApiResponse;
import com.backend.blogging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{id}/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable int id)
    {
        return new ResponseEntity<Comment>(commentService.createComment(comment,id), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId)
    {
        commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted successfully...",true),HttpStatus.OK);
    }




}
