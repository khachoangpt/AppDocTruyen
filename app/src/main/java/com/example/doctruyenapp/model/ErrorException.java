package com.example.doctruyenapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorException {
    private String timestamp;
    private String status;
    private String message;
    private List<String> errors;

    public ErrorException() {
    }

    public ErrorException(String timeStamp, String status, String message, List<String> errors) {
        this.timestamp = timeStamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorException{" +
                "timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
