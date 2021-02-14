package com.network.social_network.service;

import com.network.social_network.dto.CommentDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Comment;
import com.network.social_network.repository.CommentRepository;
import com.network.social_network.repository.SongRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public CommentService (CommentRepository commentRepository, UserRepository userRepository, SongRepository songRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    public void createComment (CommentDto commentDto) {
        var comment = new Comment(
                userRepository
                        .findById(commentDto.getUserId())
                        .orElseThrow(
                                () -> new CustomException("User with id " + commentDto.getUserId() + " not found", HttpStatus.NOT_FOUND)
                        ),
                songRepository
                        .findById(commentDto.getSongId())
                        .orElseThrow(
                                () -> new CustomException("Post with id " + commentDto.getSongId() + " not found", HttpStatus.NOT_FOUND)
                        ),
                commentDto.getText(),
                Instant.now(),
                Instant.now()
        );

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

    public List<Comment> getCommentsBySongId (Long songId) {
        return commentRepository.getCommentsBySongId(songId);
    }

    public List<Comment> getCommentsByUserId (Long userId) {
        return commentRepository.getCommentsByUserId(userId);
    }
}
