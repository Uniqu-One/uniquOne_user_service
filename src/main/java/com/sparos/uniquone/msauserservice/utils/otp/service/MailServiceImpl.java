package com.sparos.uniquone.msauserservice.utils.otp.service;

import com.sparos.uniquone.msauserservice.utils.generate.GenerateSmsOtpCode;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtProvider;
import com.sparos.uniquone.msauserservice.utils.security.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    private SimpleMailMessage preConfiguredMessage;


    @Override
    public String sendOtpMail(String toSendUserEmail) {
        //대량으로 보낼시
//        ArrayList<String> toUserList = new ArrayList<>();

        //유니콘 서비스의 수신 대상은 무조건  1명
        //단순 텍스트 구성 메일 메시지 생성 할때 이용 -> 나중에 유니콘 페이지 만들수 있으면만들기.
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper()

        //발신자 설정
//        simpleMailMessage.setFrom("melodydk89@gmail.com","유니크원 관리자");

        //수신자 설정
        simpleMailMessage.setTo(toSendUserEmail);

        //메일 제목
        simpleMailMessage.setSubject("유니크원 서비스 인증 코드");

        //메일 내용
        String otpCode = GenerateSmsOtpCode.generateCode();

        simpleMailMessage.setText("인증 코드 : " + otpCode);


        //메일 발송
        javaMailSender.send(simpleMailMessage);
        //메일 otp 저장 or update;
        return otpCode;
    }

    @Override
    public String sendUpdatePasswordMail(String sendEmail, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String body = makeUpdatePasswordLinkPage(sendEmail, request, response);

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(sendEmail);
            messageHelper.setSubject("안녕하세요 " + sendEmail + "님 유니크원 입니다.");
            messageHelper.setFrom("melodydk89@gmail.com","유니크원 관리자");
            messageHelper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "mail send Success!";
    }

    private String makeUpdatePasswordLinkPage(String email, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body>");
        sb.append("<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");
        sb.append("<h1>" + "유니크원 비밀번호 변경 링크 안내 드립니다." + "<h1><br>");
        sb.append("<br><br>");
//        sb.append("<a href='https://uniquone.shop/'>링 크 </a><br>");
        JwtToken jwtToken = JwtProvider.generateToken(email);
        sb.append("<form action='http://localhost:3000/my/user/password' method='post'> " +
                "<input type='hidden' name='token' value='"+jwtToken.getToken()+"'> " +
                "<input type='submit' value='링크'> " +
                "</form>");
        log.info("token = {} ", jwtToken.getToken());
        sb.append("</body></html>");
        return sb.toString();
    }

}
