package com.network.social_network.service;

import com.network.social_network.dto.PostDto;
import com.network.social_network.model.Post;
import com.network.social_network.repository.PostRepository;
import com.network.social_network.repository.UserRepository;
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
        //Todo: custom exception
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);

        return post;
    }

    //Todo: mapper to convert Post to PostDto and back

    public void createPost (PostDto postDto) {
        var post = new Post(
                //Todo: custom exception
                userRepository.findById(postDto.getUserId()).orElseThrow(IllegalStateException::new),
                postDto.getText(),
                postDto.getImage(),
                Instant.now(),
                Instant.now()
        );

        postRepository.save(post);
    }

    public void updatePost (Long postId, PostDto postDto) {
        //Todo: custom exception
        var post = postRepository.findById(postId).orElseThrow(IllegalStateException::new);

        post.setText(postDto.getText());
        post.setImage(postDto.getImage());
        post.setUpdated_at(Instant.now());

        postRepository.save(post);
    }

    public void deletePost (Long postId) {
        postRepository.deleteById(postId);
    }
}
