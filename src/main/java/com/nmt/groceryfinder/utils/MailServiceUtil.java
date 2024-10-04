package com.nmt.groceryfinder.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String userName;

    @Autowired
    public MailServiceUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MailException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(new InternetAddress("nmt.m10.2862001@gmail.com"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(message);

    }

    public String generateVerificationEmailContent(String email, String otpCode) {
        return "Chào bạn " + email + ",<br><br>" +
                "Chúng tôi rất vui thông báo rằng địa chỉ email của bạn đã được xác thực thành công. " +
                "<p>Tài khoản của bạn trên <strong>Tiệm tạp hóa Tân Hiệp</strong> " +
                "hiện đã được kích hoạt và sẵn sàng sử dụng.</p>" +
                "<br>" +
                "Để hoàn tất việc xác thực, vui lòng sử dụng mã OTP dưới đây để đăng nhập vào tài khoản của bạn:" +
                "<p><strong>Mã OTP: " + otpCode + "</strong></p>" +
                "<br>" +
                "Nếu bạn có bất kỳ câu hỏi nào hoặc cần hỗ trợ, vui lòng liên hệ với chúng tôi qua email " +
                "<a href=\"mailto:nmt.m10.2862001@gmail.com\">nmt.m10.2862001@gmail.com</a>." +
                "<br><br>" +
                "Cảm ơn bạn đã chọn <strong>Tiệm tạp hóa Tân Hiệp</strong>." +
                "<br><br>" +
                "Trân trọng," +
                "<p><strong>Tiệm tạp hóa Tân Hiệp</strong></p>";
    }

    public String generateAdminRegistrationEmailContent(String email, String password) {
        return "Chào bạn " + email + ",<br><br>" +
                "Tài khoản của bạn trên <strong>Tiệm tạp hóa Tân Hiệp</strong> đã được tạo thành công. " +
                "Dưới đây là mật khẩu mặc định để đăng nhập vào tài khoản của bạn:" +
                "<p><strong>Mật khẩu: " + password + "</strong></p>" +
                "<br>" +
                "Vui lòng đổi mật khẩu của bạn ngay sau khi đăng nhập để bảo mật tài khoản của bạn." +
                "<br>" +
                "Nếu bạn có bất kỳ câu hỏi nào hoặc cần hỗ trợ, vui lòng liên hệ với chúng tôi qua email " +
                "<a href=\"mailto:nmt.m10.2862001@gmail.com\">nmt.m10.2862001@gmail.com</a>." +
                "<br><br>" +
                "Cảm ơn bạn đã chọn <strong>Tiệm tạp hóa Tân Hiệp</strong>." +
                "<br><br>" +
                "Trân trọng," +
                "<p><strong>Tiệm tạp hóa Tân Hiệp</strong></p>";
    }

    public String generateResetPasswordContent(String defaultPassword) {
        return "Default Password " + ": " + defaultPassword;
    }

}
