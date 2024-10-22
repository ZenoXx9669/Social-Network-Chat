package com.jakenov.Social.Network.Chat.repositories;

import com.jakenov.Social.Network.Chat.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String username);
    Optional<User> findByNickName(String nickName);

}
