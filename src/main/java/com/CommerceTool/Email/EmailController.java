package com.CommerceTool.Email;

import com.CommerceTool.exceptions.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<ApiResponse> sendMail(@RequestBody EmailDTO emailDTO)
    {
        return emailService.sendSimpleMail(emailDTO);
    }

    @PostMapping("/sendAttachmentEmail")
    public ResponseEntity<ApiResponse> sendMailWithAttachment(@RequestBody EmailDTO emailDTO)
    {
        return emailService.sendMailWithAttachment(emailDTO);
    }
}
