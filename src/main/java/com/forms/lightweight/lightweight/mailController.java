package com.forms.lightweight.lightweight;

import com.forms.lightweight.lightweight.email.EmailContentService;
import com.forms.lightweight.lightweight.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/mail")
@RequiredArgsConstructor
public class mailController {

    private final EmailService emailService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void sendmail() throws MessagingException {
        String mailContent = EmailContentService.getConfirmationMailContent("agarguin", "ddb25422-096b-4d59-a4d7-93ce0bfecb19 ");
        emailService.sendHTMLEmail("dogu757@gmail.com","Confirm Mail",mailContent);
    }
}
