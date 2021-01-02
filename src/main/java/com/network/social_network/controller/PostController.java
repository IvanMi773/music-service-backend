package com.network.social_network.controller;

import com.network.social_network.dto.PostDto;
import com.network.social_network.model.Post;
import com.network.social_network.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController (PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAll();
    }

    @GetMapping("/{postId}")
    public Post getPostById (@PathVariable Long postId) {
        return postService.getById(postId);
    }

    @PostMapping
    public HttpStatus createPost (@RequestBody PostDto post) {
        postService.createPost(post);

        return HttpStatus.OK;
    }

    @PostMapping("/{postId}")
    public HttpStatus updatePost (@PathVariable Long postId, @RequestBody PostDto postDto) {
        postService.updatePost(postId, postDto);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{postId}")
    public HttpStatus deletePost (@PathVariable Long postId) {
        postService.deletePost(postId);

        return HttpStatus.OK;
    }
}
