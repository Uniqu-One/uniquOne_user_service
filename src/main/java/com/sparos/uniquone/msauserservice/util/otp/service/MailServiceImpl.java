package com.sparos.uniquone.msauserservice.util.otp.service;

import com.sparos.uniquone.msauserservice.util.generate.GenerateSmsOtpCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;

    @Override
    public String sendOtpMail(String toSendUserEmail) {
        //대량으로 보낼시
//        ArrayList<String> toUserList = new ArrayList<>();

        //유니콘 서비스의 수신 대상은 무조건  1명
        //단순 텍스트 구성 메일 메시지 생성 할때 이용 -> 나중에 유니콘 페이지 만들수 있으면만들기.
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        //수신자 설정
        simpleMailMessage.setTo(toSendUserEmail);

        //메일 제목
        simpleMailMessage.setSubject("유니크온 서비스 인증 코드");

        //메일 내용
        String otpCode = GenerateSmsOtpCode.generateCode();

        simpleMailMessage.setText("인증 코드 : " + otpCode);
        //메일 발송
        javaMailSender.send(simpleMailMessage);
        //메일 otp 저장 or update;
        return otpCode;
    }
}
