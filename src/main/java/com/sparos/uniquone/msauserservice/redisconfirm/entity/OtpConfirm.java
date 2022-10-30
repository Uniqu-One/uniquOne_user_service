package com.sparos.uniquone.msauserservice.redisconfirm.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash(value = "otpconfirm")
public class OtpConfirm {

//    @Id : Id가 키 값이 된다.
//    @Indexed : 값으로 검색을 할 시에 추가한다.
//    @TimeToLive : 만료시간을 설정
//   - RedishHash 어노테이션에 설정해도 된다.
//            - 값은 초 단위로 설정된다.

    @Id
    private String id;
    @Indexed
    private String otpCode;
    @TimeToLive
    private int expired;

    public OtpConfirm(String id, String otpCode, int expired){
        this.id = id;
        this.otpCode = otpCode;
        this.expired = expired;
    }
}
