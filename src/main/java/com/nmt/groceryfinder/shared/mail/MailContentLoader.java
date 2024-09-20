package com.nmt.groceryfinder.shared.mail;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 8/24/2024
 */
@Component
public class MailContentLoader {
    @Value("classpath:application.properties")
    private InputStream propertiesStream;

    private Properties properties;

    @PostConstruct
    public void init() throws IOException {
        properties = new Properties();
        properties.load(propertiesStream);
    }

    public String getSubject() {
        return properties.getProperty("email.verify.subject");
    }

    public String getHtmlContent(String email, String otpCode) {
        String template = properties.getProperty("email.verify.htmlContent");
        return template.replace("{email}", email).replace("{otpCode}", otpCode);
    }
}
