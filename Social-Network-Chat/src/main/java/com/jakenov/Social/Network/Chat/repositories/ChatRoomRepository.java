package com.jakenov.Social.Network.Chat.repositories;

import com.jakenov.Social.Network.Chat.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findBySenderAndRecipient(String senderId, String recipientId);
}
