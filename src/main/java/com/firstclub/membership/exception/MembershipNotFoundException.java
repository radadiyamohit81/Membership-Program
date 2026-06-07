package com.firstclub.membership.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MembershipNotFoundException extends RuntimeException {
    public MembershipNotFoundException(Long userId) {
        super("No active membership found for user: " + userId);
    }
}
