package com.sparos.uniquone.msauserservice.users.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparos.uniquone.msauserservice.oauth2confirm.service.Oauth2ConfirmService;
import com.sparos.uniquone.msauserservice.redisconfirm.service.RedisUtil;
import com.sparos.uniquone.msauserservice.utils.generate.GenerateSmsOtpCode;
import com.sparos.uniquone.msauserservice.utils.otp.dto.MessageDto;
import com.sparos.uniquone.msauserservice.utils.otp.service.MailService;
import com.sparos.uniquone.msauserservice.utils.otp.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final MailService mailService;

    private final SmsService smsService;
    private final Oauth2ConfirmService oauth2ConfirmService;

    private final RedisUtil redisUtil;

    @Override
    @Async
    public void sendOtpCodeByEmail(String email) {
        //메일 보내기 반환값 OTP 코드
        String sendOtpCode = mailService.sendOtpMail(email);
        //해당 이메일 보낸 OTP 코드 저장 혹은 업데이트 실행.
//        oauth2ConfirmService.saveMailOtpCode(email, Integer.valueOf(sendOtpCode));
        redisUtil.setDataExpire(sendOtpCode,email ,60 * 5L);
    }

    @Override
    public void sendOtpCodeBySms(String email, String phoneNum) {
        String otpCode = GenerateSmsOtpCode.generateCode();
        MessageDto messageDto = MessageDto.builder()
                .to(phoneNum)
                .content("유니콘 인증번호 : " + otpCode)
                .build();
        try {
            smsService.sendSms(messageDto);

            redisUtil.setDataExpire(otpCode,email ,60 * 5L);
//            oauth2ConfirmService.saveMailOtpCode(email, Integer.valueOf(otpCode));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkOtpCode(String email, int otpCode) {
        boolean isCollect = false;

        String checkEmail = redisUtil.getDate(String.valueOf(otpCode));

        if(StringUtils.hasText(checkEmail) && email.equals(checkEmail)){
            return true;
        }

        return false;
    }

    @Override
    @Async
    public String sendUpdateMailLinkPage(String email, HttpServletRequest request, HttpServletResponse response) throws Exception{
        return mailService.sendUpdatePasswordMail(email, request, response);
    }
}
