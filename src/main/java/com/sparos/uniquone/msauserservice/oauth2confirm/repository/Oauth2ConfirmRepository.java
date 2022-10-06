package com.sparos.uniquone.msauserservice.oauth2confirm.repository;

import com.sparos.uniquone.msauserservice.oauth2confirm.domain.Oauth2confirm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2ConfirmRepository extends JpaRepository<Oauth2confirm, Long> {

    Optional<Oauth2confirm> findByEmail(String email);
}
