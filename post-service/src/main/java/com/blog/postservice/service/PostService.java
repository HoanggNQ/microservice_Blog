package com.blog.postservice.service;

import com.blog.postservice.model.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO dto, String authorEmail);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long id);
    PostDTO updatePost(Long id, PostDTO dto, String authorEmail);
    void deletePost(Long id, String authorEmail);
}
