package com.sparos.uniquone.msauserservice.users.service.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    void sendOtpCodeByEmail(String email);
    void sendOtpCodeBySms(String email, String phoneNum);
    boolean checkOtpCode(String email,int otpCode);
    String sendUpdateMailLinkPage(String email, HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
