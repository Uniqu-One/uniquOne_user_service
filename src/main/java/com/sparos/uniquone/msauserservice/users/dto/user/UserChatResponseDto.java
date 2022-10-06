package com.sparos.uniquone.msauserservice.users.dto.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChatResponseDto {

    private Long userId;
    private String nickname;

}
