package com.CommerceTool.Email;

import com.CommerceTool.exceptions.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    public ResponseEntity<ApiResponse> sendSimpleMail(EmailDTO emailDTO)
    {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDTO.getRecipient());
            simpleMailMessage.setSubject(emailDTO.getSubject());
            simpleMailMessage.setText(emailDTO.getMsgBody());

            javaMailSender.send(simpleMailMessage);
            return new ResponseEntity<>(new ApiResponse("Mail send successfully",true), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(new ApiResponse("Error while Sending Mail",false), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<ApiResponse> sendMailWithAttachment(EmailDTO emailDTO)
    {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDTO.getRecipient());
            mimeMessageHelper.setText(emailDTO.getMsgBody());
            mimeMessageHelper.setSubject(emailDTO.getSubject());

            FileSystemResource file = new FileSystemResource(new File(emailDTO.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            javaMailSender.send(mimeMessage);
            return new ResponseEntity<>(new ApiResponse("Mail sent Successfully",true),HttpStatus.OK);
        }

        catch ( MessagingException e) {

            return new ResponseEntity<>(new ApiResponse("Error while sending mail!!!",false),HttpStatus.BAD_REQUEST);
        }
    }
}
