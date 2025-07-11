package com.blog.userservice.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String bio;
}
