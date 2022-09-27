package com.sparos.uniquone.msauserservice.users.entity;


import com.sparos.uniquone.msauserservice.users.typeEnum.UserGrade;
import com.sparos.uniquone.msauserservice.users.typeEnum.UserRole;
import com.sparos.uniquone.msauserservice.util.basetime.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , length = 45 , unique = true)
    private String email;
    @Column(nullable = false, length = 20)
    private String pwd;
    @Column(nullable = false, length = 20, unique = true)
    private String nickname;
    @Column(nullable = true, length = 13)
    private String phoneNum;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("varchar(5) default RED")
    private UserGrade grade;
    @ColumnDefault("int default 0")
    private Integer point;
    @ColumnDefault("int default 0")
    private Integer score;

}
