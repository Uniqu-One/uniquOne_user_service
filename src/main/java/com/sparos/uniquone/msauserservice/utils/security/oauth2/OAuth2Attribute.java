package com.sparos.uniquone.msauserservice.utils.security.oauth2;

import com.sparos.uniquone.msauserservice.users.domain.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
//OAuth2 인증후 보내주는 데이터가 각 인증 서버마다 다르기 때문에 분기 처리.
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    static OAuth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes){
        switch (provider){
            case "google" :
                return ofGoogle(attributeKey, attributes);
            case "kakao" :
                return ofKakao("email", attributes);
            case "naver" :
                return ofNaver("id", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofGoogle(String attributeKey, Map<String, Object> attributes){
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofNaver(String attributeKey, Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .attributeKey(attributeKey)
                .build();
    }

    Map<String, Object> convertToMap(){
        Map<String, Object> m = new ConcurrentHashMap<>();
        m.put("id",attributeKey);
        m.put("key",attributeKey);
        m.put("name", name);
        m.put("email",email);
        return m;
    }

    public Users toEntity(){
        return Users.builder()
                .nickname(UUID.randomUUID().toString())
                .email(email)
//                .role(UserRole.ROLES_USER)
                .build();
    }


}
