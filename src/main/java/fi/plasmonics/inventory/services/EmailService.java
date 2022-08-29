package fi.plasmonics.inventory.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService{


    @Value("${no.reply.email}")
    private String noReplyEmailAddress;
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String subject, String text, String[] to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(StringUtils.firstNonEmpty(System.getenv("SPRING_MAIL_USERNAME"), noReplyEmailAddress));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
