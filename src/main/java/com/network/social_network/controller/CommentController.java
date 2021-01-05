package com.network.social_network.controller;

import com.network.social_network.dto.CommentDto;
import com.network.social_network.model.Comment;
import com.network.social_network.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public HttpStatus createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);

        return HttpStatus.OK;
    }

    @PostMapping("/{commentId}")
    public HttpStatus editComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        commentService.updateComment(commentId, commentDto);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{commentId}")
    public HttpStatus deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return HttpStatus.OK;
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> getCommentsByUserId(@PathVariable Long userId) {
        return commentService.getCommentsByUserId(userId);
    }
}
