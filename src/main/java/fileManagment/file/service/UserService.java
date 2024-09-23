package fileManagment.file.service;

import fileManagment.file.entity.CredentialEntity;
import fileManagment.file.entity.RoleEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.enumeration.LoginType;
import fileManagment.file.securityDto.User;

public interface UserService {
    UserEntity createUser(String firstName,String lastName,String email,String authority,Integer age,String address, String gender,String userId);
    RoleEntity getRole(String roleName);
    void verifyUser(String token);
    void updateLoginAttempt(String email, LoginType loginType);
    User getUserByUserId(String userId);

    User getUserByUserEmail(String email);

    CredentialEntity getUserCredentialById(Long id);
}
