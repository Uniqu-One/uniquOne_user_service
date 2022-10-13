package com.sparos.uniquone.msauserservice.utils.otp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparos.uniquone.msauserservice.utils.otp.dto.MessageDto;
import com.sparos.uniquone.msauserservice.utils.otp.dto.request.SmsRequestDto;
import com.sparos.uniquone.msauserservice.utils.otp.dto.response.SmsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {
    private final Environment env;
//
//    private String accessKey = env.getProperty("naver-cloud-sms.accessKey");
//    private String secretKey = env.getProperty("naver-cloud-sms.secretKey");
//    private String serviceId = env.getProperty("naver-cloud-sms.serviceId");
//    private String phone = env.getProperty("naver-coud-sms.senderPhone");

    public String makeSignature(Long time) throws NoSuchAlgorithmException,
                                UnsupportedEncodingException, InvalidKeyException{
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String accessKey = env.getProperty("naver-cloud-sms.accessKey");
        String secretKey = env.getProperty("naver-cloud-sms.secretKey");
        String serviceId = env.getProperty("naver-cloud-sms.serviceId");



//        String url = "/sms/v2/services/"+this.serviceId+"/messages";
        String url = "/sms/v2/services/"+serviceId+"/messages";
        String timestamp = time.toString();
//        String accessKey = this.accessKey;
//        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    public SmsResponseDto sendSms(MessageDto messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
        Long time = System.currentTimeMillis();

        String accessKey = env.getProperty("naver-cloud-sms.accessKey");
        String phone = env.getProperty("naver-cloud-sms.senderPhone");
        String serviceId = env.getProperty("naver-cloud-sms.serviceId");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);

        SmsRequestDto request = SmsRequestDto.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content(messageDto.getContent())
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResponseDto response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDto.class);

        return response;
    }

}
