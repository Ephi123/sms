package com.project1.sms.apiException;

public class ResourceNotFoundException extends RuntimeException {
    Integer errorStatus;
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, int errorStatus) {

        super(message);
        this.errorStatus = errorStatus;
    }


    public ResourceNotFoundException() {
        super("error occurred");
    }

    int getErrorStatus(){
        return errorStatus;
    }
}
