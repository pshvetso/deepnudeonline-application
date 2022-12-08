package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.dto.ContactsDto;
import com.publab.deepnudeonlineapplication.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactsController {
    private static final String mailToContacts = "";

    private final EmailService emailService;

    @PostMapping("/contacts")
    public void contactsWithAttachment(@RequestPart("contactsData") @Valid ContactsDto contactsDto,
                                       @RequestPart(value = "attach", required = false) MultipartFile attach) {
        String[] mailTo = {mailToContacts};
        String mailSubject = String.format("Message from %s (%s)", contactsDto.getName(), contactsDto.getEmail());
        String mailText = String.format("Message from:\n%s (%s)\nText:\n%s", contactsDto.getName(), contactsDto.getEmail(), contactsDto.getText());

        if (attach == null) {
            emailService.sendSimpleMessage(mailTo, mailSubject, mailText);
        } else {
            emailService.sendMessageWithAttachment(mailTo, mailSubject, mailText, attach);
        }
    }
}
