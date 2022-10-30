package com.sparos.uniquone.msauserservice.redisconfirm.repository;


import com.sparos.uniquone.msauserservice.redisconfirm.entity.OtpConfirm;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OtpConfirmRepository extends CrudRepository<OtpConfirm,String> {
    public Optional<OtpConfirm> findByOtpCode(String otpCode);
}
