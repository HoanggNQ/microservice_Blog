package com.blog.userservice.event;

import com.blog.userservice.model.entity.UserEntity;
import com.blog.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final UserRepository userRepository;

    @KafkaListener(topics = "user_registered", groupId = "user-profile-group", containerFactory = "userKafkaListenerContainerFactory")
    public void consume(UserRegisteredEvent event) {
        log.info("📥 Nhận sự kiện Kafka: {}", event);

        userRepository.findByEmail(event.getEmail()).ifPresentOrElse(
                u -> log.warn("⚠️ Hồ sơ đã tồn tại cho {}", event.getEmail()),
                () -> {
                    UserEntity user = UserEntity.builder()
                            .email(event.getEmail())
                            .fullName(event.getFullName())
                            .build();
                    userRepository.save(user);
                    log.info("✅ Hồ sơ user được tạo: {}", user.getEmail());
                }
        );
    }
}

