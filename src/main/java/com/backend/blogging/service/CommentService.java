package com.backend.blogging.service;

import com.backend.blogging.entities.Comment;

public interface CommentService {

    Comment createComment(Comment comment,Integer id);

    void deleteComment(int id);
}
