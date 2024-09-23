package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.cache.CacheStore;
import fileManagment.file.domain.RequestContext;
import fileManagment.file.entity.ConfirmationEntity;
import fileManagment.file.entity.CredentialEntity;
import fileManagment.file.entity.RoleEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.enumeration.EventType;
import fileManagment.file.enumeration.LoginType;
import fileManagment.file.event.UserEvent;
import fileManagment.file.repository.ConfirmationRepo;
import fileManagment.file.repository.CredentialRepo;
import fileManagment.file.repository.RoleRepo;
import fileManagment.file.repository.UserRepo;
import fileManagment.file.securityDto.User;
import fileManagment.file.service.UserService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static fileManagment.file.service.impl.PasswordGeneratorService.*;
import static fileManagment.file.util.UserDtoUtil.convertToUser;
import static fileManagment.file.util.UserUtil.creatUserEntity;
import static java.time.LocalDateTime.*;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final CredentialRepo credentialRepo;
    private final ConfirmationRepo confirmationRepo;
    private final RoleRepo roleRepo;
    private final CacheStore<String,Integer> userCaches;
    private final BCryptPasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Override
    public UserEntity createUser(String firstName, String lastName, String email,String authority,Integer age,String address,String gender,String userId) {

                   if(userRepo.existsByEmail(email)){
                       RequestUtil.handleErrorResponse(request,response, new ApiException("user is already registered"),HttpStatus.BAD_REQUEST);
                       throw new ApiException("user is already registered");
                   }

                   var password = generatePaSword(8);
                   var userEntity = userRepo.save(createNewUser(firstName, lastName, email,authority,age,address,gender,userId));
                   var credentialEntity = new CredentialEntity(userEntity,encoder.encode(password));
                   var credential = credentialRepo.save(credentialEntity);
                   var confirmationEntity = new ConfirmationEntity(userEntity);
                   confirmationRepo.save(confirmationEntity);
                   publisher.publishEvent(new UserEvent(userEntity, EventType.REGISTER, Map.of("key",confirmationEntity.getToken()),password));

               return userEntity;


    }

    @Override
    public RoleEntity getRole(String roleName) {
        return roleRepo.findByNameIgnoreCase(roleName).orElseThrow(() -> new ApiException("role is not found"));
    }

    @Override
    public void verifyUser(String token) {
        var confirmationEntity = confirmationRepo.findByToken(token).orElseThrow(() -> new ApiException("token not find"));
        var userEntity = confirmationEntity.getUserEntity();
        userEntity.setEnable(true);
        userRepo.save(userEntity);
        confirmationRepo.delete(confirmationEntity);

    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        var userEntity = userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new BadCredentialsException("email or/and password  Incorrect"));
        RequestContext.setUserId(userEntity.getId());
        switch (loginType){
            case LOGIN_ATTEMPT -> {
                if(userCaches.get(userEntity.getEmail()) == null){
                    userEntity.setLoginAttempt(0);
                    userEntity.setAccountNonLocked(true);
                }
                userEntity.setLoginAttempt(userEntity.getLoginAttempt()+1);
                userCaches.put(userEntity.getEmail(),userEntity.getLoginAttempt());

                if(userCaches.get(userEntity.getEmail()) > 5){
                    userEntity.setAccountNonLocked(false);

                }

            }
            case LOGIN_SUCCESS->{
                userEntity.setAccountNonLocked(true);
                userEntity.setLoginAttempt(0);
                userEntity.setLastLogin(now());
                userCaches.evict(userEntity.getEmail());
            }
        }

        userRepo.save(userEntity);
    }

    @Override
    public User getUserByUserId(String userId) {

        UserEntity userEntity  = userRepo.findByUserId(userId).orElseThrow(() ->  new ApiException("user not find"));
        return  convertToUser(userEntity,userEntity.getRoles(),getUserCredentialById(userEntity.getId()));
    }

    @Override
    public User getUserByUserEmail(String email) {
             UserEntity userEntity = userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new ApiException("user not found"));
        return convertToUser(userEntity, userEntity.getRoles(),getUserCredentialById(userEntity.getId()));
    }


    @Override
    public CredentialEntity getUserCredentialById(Long id) {
          return credentialRepo.getCredentialByUserEntityId(id).orElseThrow(() -> new ApiException("credential not found"));

    }


    private UserEntity createNewUser(String firstName, String lastName, String email, String authority, Integer age, String address, String gender, String userId) {
        var role = getRole(authority);
        return creatUserEntity(firstName,lastName,email,age,address,gender,role,userId);

    }
}
