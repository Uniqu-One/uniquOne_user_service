package com.sparos.uniquone.msauserservice.oauth2confirm.domain;

import com.sparos.uniquone.msauserservice.util.AuditingFields.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Oauth2confirm extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false ,length = 45)
    private String email;

    @Column(nullable = true, length = 11)
    private String phoneNum;

    @Setter
    private Integer code;

}