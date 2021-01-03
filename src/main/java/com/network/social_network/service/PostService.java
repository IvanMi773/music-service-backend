package com.network.social_network.service;

import com.network.social_network.dto.PostDto;
import com.network.social_network.exception.CustomException;
import com.network.social_network.model.Post;
import com.network.social_network.repository.PostRepository;
import com.network.social_network.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService (PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAll () {
        return postRepository.findAll();
    }

    public Post getById (Long postId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new CustomException("Post with id " + postId + " not found", HttpStatus.NOT_FOUND)
                );

        return post;
    }

    //Todo: mapper to convert Post to PostDto and back

    public void createPost (PostDto postDto) {
        var post = new Post(
                userRepository
                        .findById(postDto.getUserId())
                        .orElseThrow(
                                () -> new CustomException("User with id " + postDto.getUserId() + " not found", HttpStatus.NOT_FOUND)
                        ),
                postDto.getText(),
                postDto.getImage(),
                Instant.now(),
                Instant.now()
        );

        postRepository.save(post);
    }

    public void updatePost (Long postId, PostDto postDto) {
        var post = postRepository
            .findById(postId)
            .orElseThrow(
                    () -> new CustomException("Post with id " + postId + " not found", HttpStatus.NOT_FOUND)
            );

        post.setText(postDto.getText());
        post.setImage(postDto.getImage());
        post.setUpdated_at(Instant.now());

        postRepository.save(post);
    }

    public void deletePost (Long postId) {
        postRepository.deleteById(postId);
    }
}
