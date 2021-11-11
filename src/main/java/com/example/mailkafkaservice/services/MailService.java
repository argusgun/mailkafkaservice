package com.example.mailkafkaservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


@Service
@Slf4j
public class MailService {
    public void sendMessage(String email,String message){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println(message);
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("argusguntestkafka@gmail.com", "242649Agamgw!");
            }
        });
        log.debug("get connection to mail server");
        try {

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("from@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject("Mail Subject");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html");
//
//            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//            attachmentBodyPart.attachFile(new File("pom.xml"));
//
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
//            multipart.addBodyPart(attachmentBodyPart);
//
            msg.setContent(multipart);

            Transport.send(msg);
            log.info("Send message to "+email);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
