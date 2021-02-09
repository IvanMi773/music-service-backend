package com.network.social_network.service;

import com.network.social_network.dto.CommentDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Comment;
import com.network.social_network.repository.CommentRepository;
import com.network.social_network.repository.PostRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService (CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void createComment (CommentDto commentDto) {
        //Todo: fix code
//        var comment = new Comment(
//                userRepository
//                        .findById(commentDto.getUserId())
//                        .orElseThrow(
//                                () -> new CustomException("User with id " + commentDto.getUserId() + " not found", HttpStatus.NOT_FOUND)
//                        ),
//                postRepository
//                        .findById(commentDto.getPostId())
//                        .orElseThrow(
//                                () -> new CustomException("Post with id " + commentDto.getPostId() + " not found", HttpStatus.NOT_FOUND)
//                        ),
//                commentDto.getText(),
//                Instant.now(),
//                Instant.now()
//        );
        var comment = new Comment();

        commentRepository.save(comment);
    }

    public void updateComment (Long commentId, CommentDto commentDto) {
        var comment = commentRepository
            .findById(commentId)
            .orElseThrow(
                    () -> new CustomException("Comment with id " + commentId + " not found", HttpStatus.NOT_FOUND)
            );

        comment.setText(commentDto.getText());
        comment.setUpdated_at(Instant.now());

        commentRepository.save(comment);
    }

    public void deleteComment (Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getCommentsByPostId (Long postId) {
        //Todo: fix this
        return commentRepository.getCommentsBySongId(postId);
    }

    public List<Comment> getCommentsByUserId (Long userId) {
        return commentRepository.getCommentsByUserId(userId);
    }
}
