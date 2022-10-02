package com.sparos.uniquone.msauserservice.users.repository;

import com.sparos.uniquone.msauserservice.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

}
