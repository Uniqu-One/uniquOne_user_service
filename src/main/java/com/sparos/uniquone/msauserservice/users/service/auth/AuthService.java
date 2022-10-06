package com.sparos.uniquone.msauserservice.users.service.auth;

public interface AuthService {
    void sendOtpCodeToEmail(String email);

    boolean checkOtpCode(String email,int otpCode);
}
