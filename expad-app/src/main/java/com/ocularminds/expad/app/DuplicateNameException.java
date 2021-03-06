package com.ocularminds.expad.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DuplicateNameException extends RuntimeException {

    private static final long serialVersionUID = -3915153143490977128L;

    public DuplicateNameException(String msg) {
        super(msg);
    }
}
