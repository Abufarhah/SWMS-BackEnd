package edu.birzeit.swms.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public CustomException() {
    }

    public CustomException(String message,HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus=httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "message='" + message + '\'' +
                '}';
    }

}
