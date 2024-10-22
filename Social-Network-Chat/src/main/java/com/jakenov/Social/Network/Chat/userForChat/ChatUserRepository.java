package com.jakenov.Social.Network.Chat.userForChat;

import com.jakenov.Social.Network.Chat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser,Long> {
    Optional<ChatUser> findByNickName(String nickName);
    List<ChatUser> findAllByStatus(String status);
}
