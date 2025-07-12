package com.blog.postservice.service.impl;

import com.blog.postservice.event.PostCreatedEvent;
import com.blog.postservice.event.PostEventProducer;
import com.blog.postservice.model.dto.PostDTO;
import com.blog.postservice.model.entity.PostEntity;
import com.blog.postservice.repository.PostRepository;
import com.blog.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostEventProducer eventProducer;

    @Override
    public PostDTO createPost(PostDTO dto, String authorEmail) {
        PostEntity post = PostEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .authorEmail(authorEmail)
                .build();

        PostEntity saved = postRepository.save(post);

        // Send Kafka event
        eventProducer.sendPostCreatedEvent(
                PostCreatedEvent.builder()
                        .title(saved.getTitle())
                        .authorEmail(saved.getAuthorEmail())
                        .summary(saved.getContent().substring(0, Math.min(saved.getContent().length(), 100)))
                        .build()
        );

        return toDTO(saved);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        return postRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO dto, String authorEmail) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthorEmail().equals(authorEmail)) {
            throw new RuntimeException("Unauthorized");
        }
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return toDTO(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id, String authorEmail) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getAuthorEmail().equals(authorEmail)) {
            throw new RuntimeException("Unauthorized");
        }
        postRepository.delete(post);
    }

    private PostDTO toDTO(PostEntity entity) {
        return PostDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .authorEmail(entity.getAuthorEmail())
                .build();
    }
}
