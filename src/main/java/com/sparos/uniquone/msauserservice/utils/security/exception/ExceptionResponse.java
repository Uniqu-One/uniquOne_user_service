//package com.sparos.uniquone.msauserservice.users.security.exception;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nimbusds.jose.shaded.json.JSONObject;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.http.MediaType;
//
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Objects;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ExceptionResponse {
//
//
//    private String code;
//    private int status;
//    private String message;
//    private String detail;
//
//    public ExceptionResponse(ResponseCode code) {
//        this.message = code.getMessage();
//        this.status = code.getStatus();
//        this.code = code.getCode();
//        this.detail = getDetail();
//    }
//
//    public static ExceptionResponse of(ResponseCode code) {
//        return new ExceptionResponse(code);
//    }
//
//
////    public static void error(ServletResponse response, ResponseCode code) throws IOException {
////        ObjectMapper objectMapper = new ObjectMapper();
////        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
////        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
////        httpServletResponse.setCharacterEncoding("UTF-8");
////
////        httpServletResponse.setStatus(code.getStatus());
////        httpServletResponse.getWriter().write(Objects.requireNonNull(objectMapper.writeValueAsString(ExceptionResponse.of(code))));
////    }
//
//    public static void token(ServletResponse response, String token) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ExceptionResponse exceptionResponse = new ExceptionResponse(ResponseCode.SUCCESS_CODE);
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.setStatus(ResponseCode.SUCCESS_CODE.getStatus());
//        exceptionResponse.setDetail(token);
//        httpServletResponse.getWriter().write(Objects.requireNonNull(objectMapper.writeValueAsString(exceptionResponse)));
//    }
//
//    public static void tokenResponse(HttpServletResponse response, String token, String refreshToken) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.appendField("status", ResponseCode.SUCCESS_CODE.getStatus());
//        jsonObject.appendField("code", ResponseCode.SUCCESS_CODE.getCode());
//        jsonObject.appendField("message", ResponseCode.SUCCESS_CODE.getMessage());
//        jsonObject.appendField("access_token", token);
//        jsonObject.appendField("refresh_token", refreshToken);
//        response.getWriter().print(jsonObject);
//    }
//
//    public static void error(HttpServletResponse response, ResponseCode code) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.appendField("timestamp", String.valueOf(LocalDateTime.now()));
//        jsonObject.appendField("code", code.getCode());
//        jsonObject.appendField("status", code.getStatus());
//        jsonObject.appendField("message", code.getMessage());
//        response.getWriter().print(jsonObject);
//    }
//
//    public static void error(HttpServletResponse response, ResponseCode code, String Detail) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.appendField("timestamp", String.valueOf(LocalDateTime.now()));
//        jsonObject.appendField("code", code.getCode());
//        jsonObject.appendField("status", code.getStatus());
//        jsonObject.appendField("message", code.getMessage());
//        jsonObject.appendField("Detail", Detail);
//        response.getWriter().print(jsonObject);
//    }
//
//    public static void tokenResponse(HttpServletResponse response, String token) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.appendField("status", ResponseCode.SUCCESS_CODE.getStatus());
//        jsonObject.appendField("code", ResponseCode.SUCCESS_CODE.getCode());
//        jsonObject.appendField("message", ResponseCode.SUCCESS_CODE.getMessage());
//        jsonObject.appendField("access_token", token);
//        response.getWriter().print(jsonObject);
//    }
//}
