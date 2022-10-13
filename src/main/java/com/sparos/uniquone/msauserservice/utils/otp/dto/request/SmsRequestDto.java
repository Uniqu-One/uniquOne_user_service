package com.sparos.uniquone.msauserservice.utils.otp.dto.request;

import com.sparos.uniquone.msauserservice.utils.otp.dto.MessageDto;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SmsRequestDto {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<MessageDto> messages;
}
