package com.jakenov.Social.Network.Chat.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Notification {
    private Long id;
    private String sender;
    private String recipient;
    private String content;

}
