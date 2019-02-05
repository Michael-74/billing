package ru.soyuz_kom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.soyuz_kom.entity.Email;
import ru.soyuz_kom.repository.EmailRepository;

import java.util.Optional;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    EmailRepository emailRepository;

    @Bean
    public JavaMailSender getJavaMailSender() {

        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            Optional<Email> email = emailRepository.findById(10);

            mailSender.setHost(email.get().getHost());
            mailSender.setPort(Integer.parseInt(email.get().getPort()));

            mailSender.setUsername(email.get().getLogin());
            mailSender.setPassword(email.get().getPassword());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            return mailSender;
        } catch (Exception ex) {
            System.out.println("Ошибка при создании");

            return null;
        }
    }
}
