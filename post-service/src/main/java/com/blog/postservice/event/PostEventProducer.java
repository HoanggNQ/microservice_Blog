package com.blog.postservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostEventProducer {

    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;

    public void sendPostCreatedEvent(PostCreatedEvent event) {
        kafkaTemplate.send("post_created", event);
        log.info("📤 Gửi sự kiện PostCreatedEvent: {}", event);
    }
}
