package fileManagment.file.util;

import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.entity.RoleEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.service.IdGeneratorService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalDateTime.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;

public class UserUtil {

    public static UserEntity creatUserEntity(String firstName, String lastName, String email,Integer age,String address, String gender, RoleEntity role,String userId){

                var userEntity = UserEntity.builder()
                        .userId(userId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .phone(EMPTY)
                        .bio(EMPTY)
                        .loginAttempt(0)
                        .lastLogin(now())
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .enable(true)
                        .mfa(false)
                        .age(age)
                        .address(address)
                        .gender(gender)
                        .qrCodeSecret(EMPTY)
                        .imageUrl("").build();
                userEntity.addToRole(role);

                return userEntity;


    }




    }



