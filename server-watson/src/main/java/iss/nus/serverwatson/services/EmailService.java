package iss.nus.serverwatson.services;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import iss.nus.serverwatson.models.Feedback;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String fromEmail;

    public void sendEmail(Feedback feedback) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        String subject = "[Watson] Thank you for reaching out";

        String content = 
        """
        <p> Hello, <span style='font-weight: 700'>%s</span> </p>

        <p>We have received the following from you:</p>

        <code>%s</code>
        <br>

        <p> Our bot will contact you shortly.</p>
        <br>

        <p> Beep, Boop, Beep... <p>
        <p>Watson</p>
        """.formatted(feedback.name(), feedback.content());

        helper.setFrom(fromEmail,"Watson");
        helper.setTo(feedback.email());
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
    
}
