package com.jakenov.Social.Network.Chat.controllers;

import com.jakenov.Social.Network.Chat.entities.ChatMessage;
import com.jakenov.Social.Network.Chat.entities.Notification;
import com.jakenov.Social.Network.Chat.entities.User;
import com.jakenov.Social.Network.Chat.jwt.JwtTokenProvider;
import com.jakenov.Social.Network.Chat.repositories.UserRepository;
import com.jakenov.Social.Network.Chat.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @GetMapping("/chat")
    public String showChat(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() instanceof String
                && authentication.getPrincipal().equals("anonymousUser")) {
            return "login";
        }

        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElse(new User());
        String token = tokenProvider.generateToken(user);
        model.addAttribute("user", user);
        model.addAttribute("token", token);
        return "chat";
    }


    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                 chatMessage.getRecipient(), "/queue/messages",
                new Notification(
                        savedMsg.getId(),
                        savedMsg.getSender(),
                        savedMsg.getRecipient(),
                        savedMsg.getContent()
                )
        );

    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                                                              @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
