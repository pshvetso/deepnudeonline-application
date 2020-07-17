package com.publab.deepnudeonlineapplication.controller;

import com.publab.deepnudeonlineapplication.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ContactsController {
    private static final String mailToContacts = "";

    @Autowired
    public EmailService emailService;

    @PostMapping(value = "/contacts")
    @ResponseStatus(HttpStatus.OK)
    public void contacts(@RequestParam(value = "inputName") String inputName,
                         @RequestParam(value = "inputEmail") String inputEmail,
                         @RequestParam(value = "inputText") String inputText) {
        String[] mailTo = {mailToContacts};
        String mailSubject = String.format("Message from %s (%s)", inputName, inputEmail);
        String mailText = String.format("Message from %s (%s)\n%s", inputName, inputEmail, inputText);
        emailService.sendSimpleMessage(mailTo, mailSubject, mailText);
    }

    @PostMapping(value = "/contactsattach")
    @ResponseStatus(HttpStatus.OK)
    public void contactsWithAttachment(@RequestParam(value = "inputName") String inputName,
                                        @RequestParam(value = "inputEmail") String inputEmail,
                                        @RequestParam(value = "inputText") String inputText,
                                        @RequestParam("inputGroupFile01") MultipartFile inputGroupFile01) {
        String[] mailTo = {mailToContacts};
        String mailSubject = String.format("Message from %s (%s)", inputName, inputEmail);
        String mailText = String.format("Message from %s (%s)\n%s", inputName, inputEmail, inputText);
        emailService.sendMessageWithAttachment(mailTo, mailSubject, mailText, inputGroupFile01);
    }
}
