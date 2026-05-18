package com.project1.sms.apiException;

public class ApiException extends RuntimeException {
    Integer errorStatus;
    public ApiException(String message) {
        super(message);
    }
    public ApiException(String message,int errorStatus) {

        super(message);
        this.errorStatus = errorStatus;
    }


    public ApiException() {
        super("error occurred");
    }

    int getErrorStatus(){
        return errorStatus;
    }
}
