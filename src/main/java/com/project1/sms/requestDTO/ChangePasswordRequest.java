package com.project1.sms.requestDTO;

public record ChangePasswordRequest( String userName,
                                     String oldPassword,
                                     String newPassword) {
}
