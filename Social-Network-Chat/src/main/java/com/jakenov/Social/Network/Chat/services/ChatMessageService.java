package com.jakenov.Social.Network.Chat.services;
import com.jakenov.Social.Network.Chat.entities.ChatMessage;
import com.jakenov.Social.Network.Chat.entities.Notification;
import com.jakenov.Social.Network.Chat.entities.User;
import com.jakenov.Social.Network.Chat.repositories.ChatMessageRepository;
import com.jakenov.Social.Network.Chat.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public String openChatPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() instanceof String
                && authentication.getPrincipal().equals("anonymousUser")) {
            return "login";
        }

        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElse(new User());
        model.addAttribute("user", user);
        return "chat";
    }
    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSender(), chatMessage.getRecipient(), true)
                .orElseThrow();
        chatMessage.setChat(chatId);

        ChatMessage savedMsg = repository.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient(), "/queue/messages",
                new Notification(
                        savedMsg.getId(),
                        savedMsg.getSender(),
                        savedMsg.getRecipient(),
                        savedMsg.getContent()
                )
        );
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChat).orElse(new ArrayList<>());
    }
}
