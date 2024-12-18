package com.ll.tem.domain.post.post.service;

import com.ll.tem.domain.post.post.entity.Post;
import com.ll.tem.domain.post.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }
}
