package com.publab.deepnudeonlineapplication.service;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    void sendSimpleMessage(String[] to,
                           String subject,
                           String text);

    void sendMessageWithAttachment(String[] to,
                                   String subject,
                                   String text,
                                   MultipartFile attachment);

}
