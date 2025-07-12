package com.blog.postservice.controller;

import com.blog.postservice.model.dto.PostDTO;
import com.blog.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO dto, Authentication auth) {
        PostDTO post = postService.createPost(dto, auth.getName());
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable Long id, @RequestBody PostDTO dto, Authentication auth) {
        return ResponseEntity.ok(postService.updatePost(id, dto, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        postService.deletePost(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
