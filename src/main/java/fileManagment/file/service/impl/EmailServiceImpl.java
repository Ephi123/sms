package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static fileManagment.file.util.EmailUtil.getEmailMessage;
import static fileManagment.file.util.EmailUtil.getPasswordMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private static final String NEW_USER_VERIFICATION =  "Account Verification" ;
    private static final String PASSWORD_RESET_REQUEST = "Password Reset";
    private final JavaMailSender sender;
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    @Async
    public void sendNewAccountEmail(String name, String email, String token, String password) {
       try{
           var  message = new SimpleMailMessage();
           message.setSubject(NEW_USER_VERIFICATION);
           message.setFrom(fromEmail);
           message.setTo(email);
           message.setText(getEmailMessage(name,host,token,password));
           sender.send(message);
       }catch (Exception e){
           log.error(e.getMessage());
           throw new ApiException("Unable to send Email");
       }



    }

    @Override
    @Async
    public void sendPasswordRestore(String name, String email, String token) {

        try{
            var message = new SimpleMailMessage();
            message.setSubject(PASSWORD_RESET_REQUEST);
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setText(getPasswordMessage(name,host,token));
            sender.send(message);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ApiException("Unable to send Email");
        }


    }
}
