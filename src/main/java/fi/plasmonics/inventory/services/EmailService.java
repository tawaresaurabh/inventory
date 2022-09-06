package fi.plasmonics.inventory.services;

import fi.plasmonics.inventory.events.ThresholdReachedEmailEvent;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service("emailService")
public class EmailService {


    @Value("${no.reply.email}")
    private String noReplyEmailAddress;
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration configuration;

//    public void sendSimpleMessage(String subject, String text, String[] to) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(StringUtils.firstNonEmpty(System.getenv("SPRING_MAIL_USERNAME"), noReplyEmailAddress));
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(text);
//
//            emailSender.send(message);
//        } catch (MailException exception) {
//            exception.printStackTrace();
//        }
//    }


    public void sendTemplateEmail(String subject, String[] to, ThresholdReachedEmailEvent thresholdReachedEmailEvent) throws MessagingException, TemplateException, IOException {
        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            mimeMessage.setFrom(StringUtils.firstNonEmpty(System.getenv("SPRING_MAIL_USERNAME"), noReplyEmailAddress));

            helper.setSubject(subject);
            helper.setTo(to);
            String emailContent = getEmailContent(thresholdReachedEmailEvent);
            helper.setText(emailContent, true);
            emailSender.send(mimeMessage);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    private String getEmailContent(ThresholdReachedEmailEvent thresholdReachedEmailEvent) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("thresholdReachedEmailEvent", thresholdReachedEmailEvent);
        configuration.getTemplate("email.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
