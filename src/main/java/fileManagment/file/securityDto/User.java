package fileManagment.file.securityDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileManagment.file.entity.RoleEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class User {
    private Long id;
    private Long createdBy;
    private Long updateBy;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String lastLogin;
    private String createdAt;
    private String UpdateAt;
    private String bio;
    private String imageUrl;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialNonExpired ;
    private boolean enable;
    private boolean mfa;
    private String qrCodeImage;
    private String roles;
    private String Authorities;


}
