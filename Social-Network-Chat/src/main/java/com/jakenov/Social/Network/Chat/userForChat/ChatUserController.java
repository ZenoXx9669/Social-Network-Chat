package com.jakenov.Social.Network.Chat.userForChat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatUserController {

    private final ChatUserService chatUserService;

    @MessageMapping("/chat-user.addUser")
//    @SendTo("/user/public")
    public ChatUser addUser(@Payload ChatUser user) {
        chatUserService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
//    @SendTo("/user/public")
    public ChatUser disconnectUser(@Payload ChatUser user) {
        chatUserService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<ChatUser>> findConnectedUsers() {
        return ResponseEntity.ok(chatUserService.findConnectedUsers());
    }
}
