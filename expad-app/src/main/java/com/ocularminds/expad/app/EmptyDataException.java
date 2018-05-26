package com.ocularminds.expad.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyDataException extends RuntimeException {

    private static final long serialVersionUID = -3915153143490977128L;

    public EmptyDataException(String msg) {
        super(msg);
    }
}
