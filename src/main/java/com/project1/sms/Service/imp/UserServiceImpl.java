package com.project1.sms.Service.imp;


import com.project1.sms.Service.UserService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;
import java.util.List;
import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.requestDTO.RoleRequest;
import com.project1.sms.requestDTO.UserUpdateRequest;
import com.project1.sms.responseDto.UserResponse;
import com.project1.sms.utillity.UserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserUtility userUtility;
   @Override
    public UserResponse getCurrentUser(Authentication authentication) {
        UserEntity user = userRepo.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers(int page,int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("firstName").ascending()
        );

        Page<UserEntity> users = userRepo.findAll(pageable);
        return users.stream()
                .map(UserResponse::from)
                .toList();
    }

    /**
     * permanent employer first registered here
     * if they teach need to register and have role as teacher
     */
    @Override
    public UserResponse createUser(RegisterRequest request) {
       StringBuilder specialName = new StringBuilder(request.firstName() + request.fatherName());
        String userName = specialName+"@zion.edu";
        boolean isUsernameExist = userRepo.existsByUserName(userName);
        int n = 1;
        while (isUsernameExist){
            specialName.append(n);
            userName =  specialName +"@zion.edu";
            isUsernameExist = userRepo.existsByUserName(userName);
            n++;
           }
        UserEntity userEntity =userUtility.createUser(request,userRepo,userName,"Emp_"+specialName);

        UserEntity savedEntity = userRepo.save(userEntity);

        return UserResponse.from(savedEntity);



    }

    //admin
    @Override
    public UserResponse getUser(String userId) {
        return UserResponse.from(userRepo.findByUserId(userId).orElseThrow(() -> new ApiException("user not found")));
    }


// admin
    @Override
    public void updateUser(String userId , UserUpdateRequest request) {
        UserEntity user = userRepo.findByUserId(userId).orElseThrow(() -> new ApiException("user Not Found"));
        user.setFirstName(request.firstName());
        user.setMidlName(request.fatherName());
        user.setLastName(request.grandFName());
        user.setPhone(request.phoneNum());
        user.setEmail(request.email());

        userRepo.save(user);

    }


    //admin
    @Override
    public void updateUserActiveStatus(String userId, Active status) {
        UserEntity user =  userRepo.findByUserId(userId).orElseThrow(() -> new ApiException("user not found"));
        user.setIsActive(status);
        userRepo.save(user);

    }
 //admin
    @Override
    public void addRoles(String userId, RoleRequest request) {
        UserEntity user = userRepo.findByUserId(userId).orElseThrow(() -> new ApiException("user not found"));
        user.setRoles(request.roles());
        userRepo.save(user);


    }

    //Admin
    @Override
    public List<UserResponse> getUserByRole(Role role) {
        return userRepo.findByRoleOrderByUsernameAtAsc(role).
                stream().map(UserResponse::from).toList();
    }

}
