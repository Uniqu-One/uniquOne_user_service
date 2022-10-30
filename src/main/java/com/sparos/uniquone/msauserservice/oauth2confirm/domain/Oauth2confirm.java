package com.sparos.uniquone.msauserservice.oauth2confirm.domain;

import com.sparos.uniquone.msauserservice.utils.AuditingFields.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Oauth2confirm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,length = 45)
    private String email;

    @Column(nullable = true, length = 11)
    private String phoneNum;

    @Setter
    private Integer code;

}
