package com.sparos.uniquone.msauserservice.users.service.auth;

import com.sparos.uniquone.msauserservice.oauth2confirm.service.Oauth2ConfirmService;
import com.sparos.uniquone.msauserservice.util.otp.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final MailService mailService;
    private final Oauth2ConfirmService oauth2ConfirmService;
    @Override
    public void sendOtpCodeToEmail(String email) {
        //메일 보내기 반환값 OTP 코드
        String sendOtpCode = mailService.sendOtpMail(email);
        //해당 이메일 보낸 OTP 코드 저장 혹은 업데이트 실행.
        oauth2ConfirmService.saveMailOtpCode(email,Integer.valueOf(sendOtpCode));
    }

    @Override
    public boolean checkOtpCode(String email, int otpCode) {
        return oauth2ConfirmService.checkOtpCode(email, otpCode);
    }
}
