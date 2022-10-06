package com.sparos.uniquone.msauserservice.oauth2confirm.service;

import com.sparos.uniquone.msauserservice.oauth2confirm.domain.Oauth2confirm;
import com.sparos.uniquone.msauserservice.oauth2confirm.repository.Oauth2ConfirmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Oauth2ConfirmServiceImpl implements Oauth2ConfirmService {

    private final Oauth2ConfirmRepository oauth2ConfirmRepository;

    @Override
    public int saveMailOtpCode(String email, int otpCode) {
        //해당 이메일로 저장된 otp 코드가 있는지 확인.
        Optional<Oauth2confirm> oOauth2confirm = oauth2ConfirmRepository.findByEmail(email);
        //있으면 수정후 저장 없으면 생성후 저장.
        emailOtpSaveOrUpdate(oOauth2confirm, email, otpCode);

        return otpCode;
    }

    @Override
    public boolean checkOtpCode(String email, int otpCode) {
        //email 만 찾을것인지 email + where opt 코드 조건을 할것인지 생각.
        //스트림으로 할수 있을거 같은데 오후에 생각
        Integer code = oauth2ConfirmRepository.findByEmail(email).get().getCode();

        if(code == otpCode)
            return true;

        return false;
    }

    private Oauth2confirm emailOtpSaveOrUpdate(Optional<Oauth2confirm> oOauth2confirm, String email, int otpCode) {
        Oauth2confirm oauth2confirm;
        if (oOauth2confirm.isPresent()) {
            oauth2confirm = oOauth2confirm.get();
            oauth2confirm.setCode(otpCode);
        } else {

            oauth2confirm = Oauth2confirm.builder()
                    .email(email)
                    .code(otpCode)
                    .build();
        }
        return oauth2ConfirmRepository.save(oauth2confirm);
    }
}
