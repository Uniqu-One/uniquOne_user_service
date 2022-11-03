package com.sparos.uniquone.msauserservice.utils.otp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MailService {

    String sendOtpMail(String sendEmail);
    String sendUpdatePasswordMail(String sendEmail, HttpServletRequest request, HttpServletResponse response)throws Exception;
}
