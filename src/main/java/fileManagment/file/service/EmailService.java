package fileManagment.file.service;

public interface EmailService {
    void sendNewAccountEmail(String name, String email, String token, String password);
    void sendPasswordRestore(String name,String email, String token);
}
