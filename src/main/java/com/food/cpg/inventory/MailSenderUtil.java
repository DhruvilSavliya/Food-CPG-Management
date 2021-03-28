package com.food.cpg.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    // I found the email sending function code at https://mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
    // I have modified it by providing arguments to the sendEmail() which are 'to', 'subject', 'bodyText'
    void sendEmail(String to, String subject, String bodyText) {

        SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(bodyText);

            javaMailSender.send(msg);

    }
}
