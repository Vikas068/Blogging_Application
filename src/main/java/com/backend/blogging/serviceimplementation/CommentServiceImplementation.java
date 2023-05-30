package com.backend.blogging.serviceimplementation;

import com.backend.blogging.entities.Comment;
import com.backend.blogging.entities.Post;
import com.backend.blogging.repository.CommentRepository;
import com.backend.blogging.repository.PostRepository;
import com.backend.blogging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment createComment(Comment comment, Integer id) {
        Post post=this.postRepository.findById(id).get();
        comment.setPost(post);
        return this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}
