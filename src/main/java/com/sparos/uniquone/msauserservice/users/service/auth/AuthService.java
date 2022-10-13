package com.sparos.uniquone.msauserservice.users.service.auth;

public interface AuthService {
    void sendOtpCodeByEmail(String email);

    void sendOtpCodeBySms(String email, String phoneNum);

    boolean checkOtpCode(String email,int otpCode);
}
