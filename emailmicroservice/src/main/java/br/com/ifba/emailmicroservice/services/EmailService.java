package br.com.ifba.emailmicroservice.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.ifba.emailmicroservice.DTOs.EmailDTO;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        // this.emailRepository = emailRepository;
    }

    public EmailDTO sendEmail(EmailDTO dto) {
                
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(dto.mailFrom());
        message.setTo(dto.mailTo());
        message.setSubject(dto.mailSubject());
        message.setText(dto.mailText());
        
        mailSender.send(message);

        return dto;
    }
}
