package com.sparos.uniquone.msauserservice.utils.otp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageDto {
    String to;
    String content;
}
