package com.sparos.uniquone.msauserservice.users.domain;


import com.sparos.uniquone.msauserservice.users.typeEnum.UserGrade;
import com.sparos.uniquone.msauserservice.users.typeEnum.UserRole;
import com.sparos.uniquone.msauserservice.util.AuditingFields.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , length = 45 , unique = true)
    private String email;
    @Column(nullable = true, length = 100)
    @Setter
    private String pwd;
    @Setter
    @Column(nullable = false, length = 100, unique = true)
    private String nickname;
    @Setter
    @Column(nullable = true, length = 13)
    private String phoneNum;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(25) default 'ROLES_USER'")
    private UserRole role;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(5) default 'RED'")
    private UserGrade grade;
    @Setter
    @Column(columnDefinition = "int default 0")
    private Integer point;
    @Setter
    @Column(columnDefinition = "int default 0")
    private Integer score;

}
