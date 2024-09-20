package com.nmt.groceryfinder.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class RestErrorResponse {
    private int status;
    private String title;
    private String error;
    private String message;
    private String detail;
    private LocalDateTime timestamp;
    private String path;

    public RestErrorResponse(
            int status,
            String title,
            String error,
            String message,
            String detail,
            LocalDateTime timestamp,
            String path
    ) {
        this.status = status;
        this.title = title;
        this.error = error;
        this.message = message;
        this.detail = detail;
        this.timestamp = timestamp;
        this.path = path;
    }
}
