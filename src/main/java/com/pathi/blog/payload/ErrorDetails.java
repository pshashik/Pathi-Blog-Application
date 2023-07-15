package com.pathi.blog.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String status;
    private String error;
    private String message;
    private String path;

    public ErrorDetails(Date timestamp,String status,String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }
}
