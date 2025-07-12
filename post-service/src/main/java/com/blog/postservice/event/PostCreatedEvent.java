package com.blog.postservice.event;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreatedEvent implements Serializable {
    private String title;
    private String authorEmail;
    private String summary;
}
