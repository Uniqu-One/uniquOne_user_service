package com.sparos.uniquone.msauserservice.users.repository;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<Users> findByEmail(String email);

    Optional<Users> findByNickname(String nickName);
}
