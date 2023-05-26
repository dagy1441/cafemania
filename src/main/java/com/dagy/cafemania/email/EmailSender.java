package com.dagy.cafemania.email;

import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailSender {
    void sendMail(NotificationEmail notificationEmail);

    void sendSimpleMail(String from, String to, String subject, String body, List<String> cc);

    void sendMail(String to, String email) throws MessagingException;
}
