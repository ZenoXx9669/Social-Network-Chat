package com.jakenov.Social.Network.Chat.services;
import com.jakenov.Social.Network.Chat.entities.ChatRoom;
import com.jakenov.Social.Network.Chat.repositories.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        return chatRoomRepository
                .findBySenderAndRecipient(senderId, recipientId)
                .map(ChatRoom::getChat)
                .or(() -> {
                    if(createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, recipientId);
                        return Optional.of(chatId);
                    }

                    return  Optional.empty();
                });
    }

    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s_%s", senderId, recipientId);

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chat(chatId)
                .sender(senderId)
                .recipient(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chat(chatId)
                .sender(recipientId)
                .recipient(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}
