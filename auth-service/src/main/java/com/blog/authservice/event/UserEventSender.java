package com.blog.authservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventSender {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        kafkaTemplate.send("user_registered", event);
    }
}
