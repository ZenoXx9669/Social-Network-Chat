package com.jakenov.Social.Network.Chat.userForChat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatUserService {
    private final ChatUserRepository repository;

    public void saveUser(ChatUser user) {
        ChatUser chatUser = repository.findByNickName(user.getNickName()).orElse(null);
        if (chatUser == null) {
            user.setStatus("ONLINE");
            repository.save(user);
        }else {
            chatUser.setStatus("ONLINE");
            repository.save(chatUser);
        }
    }

    public void disconnect(ChatUser user) {
        var storedUser = repository.findByNickName(user.getNickName()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus("OFFLINE");
            repository.save(storedUser);
        }
    }

    public List<ChatUser> findConnectedUsers() {
        return repository.findAllByStatus("ONLINE");
    }
}
