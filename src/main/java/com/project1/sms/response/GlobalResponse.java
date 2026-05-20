package com.project1.sms.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

/**
 * Uniform API response wrapper for all endpoints.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GlobalResponse<T>(
        Instant timestamp,
        int status,
        String message,
        T data,
        Map<String, String> errors
) {

    public static <T> GlobalResponse<T> success(HttpStatus status, String message, T data) {
        return new GlobalResponse<>(Instant.now(), status.value(), message, data, null);
    }

    public static <T> GlobalResponse<T> success(String message, T data) {
        return success(HttpStatus.OK, message, data);
    }

    public static GlobalResponse<Void> failure(HttpStatus status, String message) {
        return new GlobalResponse<>(Instant.now(), status.value(), message, null, null);
    }

    public static GlobalResponse<Void> validationFailure(String message, Map<String, String> errors) {
        return new GlobalResponse<>(Instant.now(), HttpStatus.BAD_REQUEST.value(), message, null, errors);
    }
}