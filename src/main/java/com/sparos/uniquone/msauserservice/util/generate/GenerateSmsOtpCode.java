package com.sparos.uniquone.msauserservice.util.generate;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class GenerateSmsOtpCode {
    private GenerateSmsOtpCode(){}

    public static String generateCode(){
        String code;

        try{
            SecureRandom random = SecureRandom.getInstanceStrong();

            //0~8999 사이값 + 1000
            int c = random.nextInt(9000) + 1000;

            code = String.valueOf(c);

        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Problem sms otp code !");
        }

        return code;
    }
}
