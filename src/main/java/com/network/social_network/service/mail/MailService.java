package com.network.social_network.service.mail;

import com.network.social_network.exception.CustomException;
import com.network.social_network.model.VerificationMail;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder contentBuilder;

    public MailService (JavaMailSender mailSender, MailContentBuilder contentBuilder) {
        this.mailSender = mailSender;
        this.contentBuilder = contentBuilder;
    }

    @Async
    public void sendMail(VerificationMail mail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("network@gmail.com");
            messageHelper.setTo(mail.getRecipient());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(contentBuilder.build(mail.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            System.out.println("Email send");
        } catch (MailException e) {
            throw new CustomException("Could not send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
