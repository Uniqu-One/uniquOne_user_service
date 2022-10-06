package com.sparos.uniquone.msauserservice.oauth2confirm.service;

public interface Oauth2ConfirmService {
    //메일 OTP 저장.
    int saveMailOtpCode(String email, int otpCode);

    boolean checkOtpCode(String email, int otpCode);

}
