package fileManagment.file.service.impl;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static fileManagment.file.constant.Constant.CHARACTER;

@Service
public class PasswordGeneratorService {

    private static final SecureRandom random = new SecureRandom();

    public static String generatePaSword(int length){
        StringBuilder password = new StringBuilder(length);
        for(int i=0; i<length; i++){
            var index = random.nextInt(CHARACTER.length());
            password.append(CHARACTER.charAt(index));

        }
        return password.toString();
    }

}
