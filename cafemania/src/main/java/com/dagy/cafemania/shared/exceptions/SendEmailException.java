package com.dagy.cafemania.shared.exceptions;

public class SendEmailException extends RuntimeException {
    public SendEmailException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SendEmailException(String exMessage) {
        super(exMessage);
    }
}
