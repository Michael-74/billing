package ru.soyuz_kom.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import ru.soyuz_kom.entity.Email;
import ru.soyuz_kom.repository.EmailRepository;
import ru.soyuz_kom.service.IMessage;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Properties;

@Service
public class MessageMailServiceImpl implements IMessage {

    @Autowired
    EmailRepository emailRepository;

    private String emailFrom = null;

    public void send(String[] to, String message) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();

        for(String email: to) {
            try {
                simpleMessage.setFrom(this.emailFrom);
                simpleMessage.setTo(email);
                simpleMessage.setSubject("Союз-ком");
                simpleMessage.setText(message);
                this.mailSender().send(simpleMessage);
            } catch (NullPointerException nullEx) {
                throw new NullPointerException();
                // TODO:: log - отлавливать ошибки
                //System.out.println("Ошибка при отправке почты: " + nullEx);
            } catch (Exception ex) {
                // TODO:: log - отлавливать ошибки
                System.out.println("Ошибка при отправке почты: " + ex);
            }
        }
    }

    private JavaMailSenderImpl mailSender() {

        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            List<Email> emails = emailRepository.findAllByIsStatus();

            if(emails.size() != 0) {
                Email email = emails.get(0);
                this.emailFrom = email.getLogin();
                mailSender.setHost(email.getHost());
                mailSender.setPort(Integer.parseInt(email.getPort()));
                mailSender.setUsername(email.getLogin());
                mailSender.setPassword(email.getPassword());

                Properties props = mailSender.getJavaMailProperties();
                props.put("mail.transport.protocol", "smtp");
                String type = String.valueOf(email.getTypeEncryption()).equals("ssl") ? "true" : "false";
                props.put("mail.smtp.ssl.enable", type);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.debug", "false");
            } else {
                return null;
            }

            return mailSender;

        } catch (Exception ex) {
            System.out.println("Ошибка при создании mailSender");

            return null;
        }
    }
}
