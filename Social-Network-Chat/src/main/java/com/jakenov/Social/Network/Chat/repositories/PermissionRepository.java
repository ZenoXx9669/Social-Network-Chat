package com.jakenov.Social.Network.Chat.repositories;

import com.jakenov.Social.Network.Chat.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
