package com.sparos.uniquone.msauserservice.notiKeyword.entity;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class NotiKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;

    @Column(nullable = false, length = 15)
    private String word;
}
